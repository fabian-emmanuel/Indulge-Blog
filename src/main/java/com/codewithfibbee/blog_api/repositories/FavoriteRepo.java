package com.codewithfibbee.blog_api.repositories;

import com.codewithfibbee.blog_api.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    Favorite findFavoriteById(Long id);
}
