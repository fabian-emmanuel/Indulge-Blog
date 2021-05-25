package com.codewithfibbee.blog_api.controllers;

import com.codewithfibbee.blog_api.models.Post;
import com.codewithfibbee.blog_api.models.User;
import com.codewithfibbee.blog_api.service.PostService;
import com.codewithfibbee.blog_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post, @PathVariable Long userId){
        var user = Optional.ofNullable(userService.getUserById(userId));
        if(user.isEmpty()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else{
            post.setUser(user.get());
            postService.createPost(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }

    @PutMapping("/{userId}/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long userId, @PathVariable Long postId, @Valid @RequestBody Post updatePost){
        var user = Optional.ofNullable(userService.getUserById(userId));
        var post = Optional.ofNullable(postService.getPostById(postId));
        if (user.isPresent() && post.isPresent() && post.get().getUser().getId().equals(userId)){
            var postToUpdate = post.get();
            postToUpdate.setContent(updatePost.getContent());
            postToUpdate.setTitle(updatePost.getTitle());
            postService.createPost(postToUpdate);
            return new ResponseEntity<>(postToUpdate, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @PathVariable Long userId){
        var user = Optional.ofNullable(userService.getUserById(userId));
        var post = Optional.ofNullable(postService.getPostById(postId));
        if (user.isPresent() && post.isPresent() && post.get().getUser().getId().equals(userId)){
            var postToDelete = post.get();
            postService.deletePost(postToDelete);
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
