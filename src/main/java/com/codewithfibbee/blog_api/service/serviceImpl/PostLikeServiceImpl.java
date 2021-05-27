package com.codewithfibbee.blog_api.service.serviceImpl;

import com.codewithfibbee.blog_api.models.PostLike;
import com.codewithfibbee.blog_api.repositories.PostLikeRepo;
import com.codewithfibbee.blog_api.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {
    PostLikeRepo postLikeRepo;

    @Autowired
    public PostLikeServiceImpl(PostLikeRepo postLikeRepo) {
        this.postLikeRepo = postLikeRepo;
    }

    @Override
    public void save(PostLike like) {
        postLikeRepo.save(like);
    }

    @Override
    public PostLike getLikeById(Long id) {
        return postLikeRepo.findPostLikeById(id);
    }

    @Override
    public void delete(PostLike like) {
        postLikeRepo.delete(like);
    }
}
