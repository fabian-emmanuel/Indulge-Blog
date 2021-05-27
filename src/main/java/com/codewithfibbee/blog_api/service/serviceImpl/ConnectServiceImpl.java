package com.codewithfibbee.blog_api.service.serviceImpl;

import com.codewithfibbee.blog_api.models.Connect;
import com.codewithfibbee.blog_api.repositories.ConnectRepo;
import com.codewithfibbee.blog_api.service.ConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectServiceImpl implements ConnectService {
    ConnectRepo connectRepo;

    @Autowired
    public ConnectServiceImpl(ConnectRepo connectRepo) {
        this.connectRepo = connectRepo;
    }


    @Override
    public Connect getConnectById(Long id) {
        return connectRepo.findConnectById(id);
    }

    @Override
    public void save(Connect connect) {
        connectRepo.save(connect);
    }

    @Override
    public void delete(Connect connect) {
        connectRepo.delete(connect);
    }
}
