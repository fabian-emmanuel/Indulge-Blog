package com.codewithfibbee.blog_api.service.serviceImpl;

import com.codewithfibbee.blog_api.models.Post;
import com.codewithfibbee.blog_api.repositories.PostRepo;
import com.codewithfibbee.blog_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;

    @Autowired
    public PostServiceImpl(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public void createPost(Post post) {
        postRepo.save(post);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepo.findPostById(postId);
    }

    @Override
    public void deletePost(Post post) {
        postRepo.delete(post);
    }
}
