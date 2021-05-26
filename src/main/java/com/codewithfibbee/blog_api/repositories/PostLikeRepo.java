package com.codewithfibbee.blog_api.repositories;

import com.codewithfibbee.blog_api.models.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike, Long> {
    PostLike findPostLikeByPostLike(PostLike like);
}
