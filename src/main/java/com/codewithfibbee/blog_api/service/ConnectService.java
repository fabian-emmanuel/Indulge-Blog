package com.codewithfibbee.blog_api.service;

import com.codewithfibbee.blog_api.models.Connect;

public interface ConnectService {
    Connect getConnectById(Long id);

    void save(Connect connect);

    void delete(Connect connect);
}
