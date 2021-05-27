package com.codewithfibbee.blog_api.controllers;

import com.codewithfibbee.blog_api.models.Connect;
import com.codewithfibbee.blog_api.service.ConnectService;
import com.codewithfibbee.blog_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController @RequestMapping("/connects")
public class ConnectController {
    ConnectService connectService;
    UserService userService;

    @Autowired
    public ConnectController(ConnectService connectService, UserService userService) {
        this.connectService = connectService;
        this.userService = userService;
    }

    @PostMapping("/{user1_id}/follow/{user2_id}")
    public ResponseEntity<Connect> follow(@PathVariable Long user1_id, @PathVariable Long user2_id, @Valid Connect connect){
        var follower = Optional.ofNullable(userService.getUserById(user1_id));
        var user = Optional.ofNullable(userService.getUserById(user2_id));
        var newConnect = Optional.ofNullable(connectService.getConnectById(connect.getId()));
        if (follower.isPresent() && user.isPresent() && newConnect.isEmpty()){
            connect.setFollowerId(follower.get().getId());
            connect.setUserId(user.get().getId());
            connectService.save(connect);
            return new ResponseEntity<>(connect, HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @DeleteMapping("/{user1_id}/follow/{user2_id}")
    public ResponseEntity<?> unfollow(@PathVariable Long user1_id, @PathVariable Long user2_id, @Valid Connect connect){
        var follower = Optional.ofNullable(userService.getUserById(user1_id));
        var user = Optional.ofNullable(userService.getUserById(user2_id));
        var newConnect = Optional.ofNullable(connectService.getConnectById(connect.getId()));
        if (follower.isPresent() && user.isPresent() && newConnect.isPresent()){
            connectService.delete(connect);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
