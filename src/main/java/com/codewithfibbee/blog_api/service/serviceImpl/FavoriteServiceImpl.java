package com.codewithfibbee.blog_api.service.serviceImpl;

import com.codewithfibbee.blog_api.models.Favorite;
import com.codewithfibbee.blog_api.repositories.FavoriteRepo;
import com.codewithfibbee.blog_api.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    FavoriteRepo favoriteRepo;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepo favoriteRepo) {
        this.favoriteRepo = favoriteRepo;
    }

    @Override
    public Favorite getFavoriteById(Long id) {
        return favoriteRepo.findFavoriteById(id);
    }

    @Override
    public void delete(Favorite favorite) {
        favoriteRepo.delete(favorite);
    }

    @Override
    public void save(Favorite favorite) {
        favoriteRepo.save(favorite);
    }
}
