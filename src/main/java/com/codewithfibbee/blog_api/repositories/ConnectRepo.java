package com.codewithfibbee.blog_api.repositories;

import com.codewithfibbee.blog_api.models.Connect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectRepo extends JpaRepository<Connect, Long> {
 Connect findConnectById(Long id);
}
