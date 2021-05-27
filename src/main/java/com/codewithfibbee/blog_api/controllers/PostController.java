package com.codewithfibbee.blog_api.controllers;

import com.codewithfibbee.blog_api.models.Favorite;
import com.codewithfibbee.blog_api.models.Post;
import com.codewithfibbee.blog_api.models.PostLike;
import com.codewithfibbee.blog_api.service.FavoriteService;
import com.codewithfibbee.blog_api.service.PostLikeService;
import com.codewithfibbee.blog_api.service.PostService;
import com.codewithfibbee.blog_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final PostLikeService postLikeService;
    private final FavoriteService favoriteService;

    @Autowired
    public PostController(UserService userService, PostService postService, PostLikeService postLikeService, FavoriteService favoriteService) {
        this.userService = userService;
        this.postService = postService;
        this.postLikeService = postLikeService;
        this.favoriteService = favoriteService;
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

    @PostMapping("/{userId}/likes/{postId}")
    public ResponseEntity<PostLike> likePost(@PathVariable Long postId, @PathVariable Long userId, @Valid PostLike like){
        var user = Optional.ofNullable(userService.getUserById(userId));
        var post = Optional.ofNullable(postService.getPostById(postId));
        var liked = Optional.ofNullable(postLikeService.getLikeById(like.getId()));
        if (user.isPresent() && post.isPresent() && liked.isEmpty()){
            like.setUser(user.get());
            like.setPost(post.get());
            postLikeService.save(like);
            return new ResponseEntity<>(like, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{userId}/likes/{postId}")
    public ResponseEntity<?> unlikePost(@PathVariable Long userId, @PathVariable Long postId, @Valid PostLike like){
        var user = Optional.ofNullable(userService.getUserById(userId));
        var post = Optional.ofNullable(postService.getPostById(postId));
        var liked = Optional.ofNullable(postLikeService.getLikeById(like.getId()));
        if (user.isPresent() && post.isPresent() && liked.isPresent()){
            postLikeService.delete(like);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }



    @PostMapping("/{userid}/favorites/{postId}")
    public ResponseEntity<Favorite> addFavorite(@PathVariable Long postId, @PathVariable Long userid, @Valid Favorite favorite){
        var user = Optional.ofNullable(userService.getUserById(userid));
        var post = Optional.ofNullable(postService.getPostById(postId));
        var newFavorite = Optional.ofNullable(favoriteService.getFavoriteById(favorite.getId()));
        if (user.isPresent() && post.isPresent() && newFavorite.isEmpty()){
            favorite.setUser(user.get());
            favorite.setPost(post.get());
            favoriteService.save(favorite);
            return new ResponseEntity<>(favorite, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{userid}/favorites/{postId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long postId, @PathVariable Long userid, @Valid Favorite favorite){
        var user = Optional.ofNullable(userService.getUserById(userid));
        var post = Optional.ofNullable(postService.getPostById(postId));
        var newFavorite = Optional.ofNullable(favoriteService.getFavoriteById(favorite.getId()));
        if (user.isPresent() && post.isPresent() && newFavorite.isPresent()) {
            favoriteService.delete(favorite);
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
