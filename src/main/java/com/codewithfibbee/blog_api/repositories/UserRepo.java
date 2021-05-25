package com.codewithfibbee.blog_api.repositories;

import com.codewithfibbee.blog_api.models.User;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUserName(String userName);
    User findUserById(Long id);
    User findUserByEmailAndPassword(String email, String password);
    List<User> findAllByIsDeactivated(Boolean bool);
}
