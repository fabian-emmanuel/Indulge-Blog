package com.codewithfibbee.blog_api.repositories;

import com.codewithfibbee.blog_api.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Post findPostById(Long id);
}
