package com.codewithfibbee.blog_api.service;

import com.codewithfibbee.blog_api.models.Favorite;

public interface FavoriteService {
    Favorite getFavoriteById(Long id);

    void delete(Favorite favorite);

    void save(Favorite favorite);
}
