package com.codewithfibbee.blog_api.repositories;

import com.codewithfibbee.blog_api.models.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepo extends JpaRepository<CommentLike, Long> {
    CommentLike findByCommentLike(CommentLike like);
}
