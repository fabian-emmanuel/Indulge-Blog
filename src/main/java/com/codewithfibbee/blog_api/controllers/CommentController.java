package com.codewithfibbee.blog_api.controllers;

import com.codewithfibbee.blog_api.models.Comment;
import com.codewithfibbee.blog_api.models.CommentLike;
import com.codewithfibbee.blog_api.service.CommentLikeService;
import com.codewithfibbee.blog_api.service.CommentService;
import com.codewithfibbee.blog_api.service.PostService;
import com.codewithfibbee.blog_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    CommentService commentService;
    UserService userService;
    PostService postService;
    CommentLikeService likeService;

    @Autowired
    public CommentController(CommentService commentService,
                             UserService userService,
                             PostService postService,
                             CommentLikeService likeService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
       List<Comment> comments = commentService.getAllComments();
       return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long userId, @PathVariable Long postId, @Valid @RequestBody Comment comment) {
        var user = Optional.ofNullable(userService.getUserById(userId));
        var post = Optional.ofNullable(postService.getPostById(postId));
        if (user.isPresent() && post.isPresent()) {
        comment.setUser(user.get());
        comment.setPost(post.get());
        commentService.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/{userId}/likes/{commentId}")
    public ResponseEntity<CommentLike> likeComment(@PathVariable Long commentId, @PathVariable Long userId, @Valid @RequestBody CommentLike like){
        var user = Optional.ofNullable(userService.getUserById(userId));
        var comment = Optional.ofNullable(commentService.getCommentById(commentId));
        var liked = Optional.ofNullable(likeService.getLike(like));
        if (user.isPresent() && comment.isPresent()){
            if (liked.isPresent()) likeService.delete(like);
            like.setUser(user.get());
            like.setComment(comment.get());
            likeService.save(like);
            return new ResponseEntity<>(like, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}
