package com.codewithfibbee.blog_api.service;

import com.codewithfibbee.blog_api.models.CommentLike;

public interface CommentLikeService {
    void save(CommentLike like);

    CommentLike getLike(CommentLike like);

    void delete(CommentLike like);
}
