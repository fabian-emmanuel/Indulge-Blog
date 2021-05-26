package com.codewithfibbee.blog_api.service;


import com.codewithfibbee.blog_api.models.PostLike;

public interface PostLikeService {
    void save(PostLike like);

    PostLike getLike(PostLike like);

    void delete(PostLike like);
}
