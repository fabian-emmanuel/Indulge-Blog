package com.codewithfibbee.blog_api.service;


import com.codewithfibbee.blog_api.models.PostLike;

public interface PostLikeService {
    void save(PostLike like);

    PostLike getLikeById(Long id);

    void delete(PostLike like);
}
