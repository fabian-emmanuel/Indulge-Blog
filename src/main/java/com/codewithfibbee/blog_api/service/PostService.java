package com.codewithfibbee.blog_api.service;

import com.codewithfibbee.blog_api.models.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    void createPost(Post post);

    Post getPostById(Long postId);

    void deletePost(Post post);
}
