package com.codewithfibbee.blog_api.repositories;

import com.codewithfibbee.blog_api.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);
}
