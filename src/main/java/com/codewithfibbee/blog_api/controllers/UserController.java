package com.codewithfibbee.blog_api.controllers;


import com.codewithfibbee.blog_api.models.User;
import com.codewithfibbee.blog_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController @RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        var user = Optional.ofNullable(userService.getUserById(userId));
        if(user.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(user.get(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        var checkUser = Optional.ofNullable(userService.getUserByUsername(user.getUserName()));
        if(checkUser.isEmpty()){
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(user.getEmail());
            if (!matcher.matches()){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.IM_USED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody User user, HttpSession session){
        var userExist = Optional.ofNullable(userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword()));
        if(userExist.isPresent()){
            session.setAttribute("user", user);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @Valid @RequestBody User user){
        var newUser = Optional.ofNullable(userService.getUserById(userId));
        if(newUser.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else if (!newUser.get().equals(user)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else {
            userService.delete(user);
            return ResponseEntity.noContent().build();
        }
    }

}
