package com.codewithfibbee.blog_api.service.serviceImpl;

import com.codewithfibbee.blog_api.models.CommentLike;
import com.codewithfibbee.blog_api.repositories.CommentLikeRepo;
import com.codewithfibbee.blog_api.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    CommentLikeRepo commentLikeRepo;

    @Autowired
    public CommentLikeServiceImpl(CommentLikeRepo commentLikeRepo) {
        this.commentLikeRepo = commentLikeRepo;
    }

    @Override
    public void save(CommentLike like) {
        commentLikeRepo.save(like);
    }

    @Override
    public CommentLike getLike(CommentLike like) {
        return commentLikeRepo.findByCommentLike(like);
    }

    @Override
    public void delete(CommentLike like) {
        commentLikeRepo.delete(like);
    }
}
