package com.codewithfibbee.blog_api.service;

import com.codewithfibbee.blog_api.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();

    void save(Comment comment);

    Comment getCommentById(Long id);
}
