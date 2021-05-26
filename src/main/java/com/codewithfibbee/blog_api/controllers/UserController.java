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

    @PostMapping("/{userId}")
    public ResponseEntity<User> login(HttpSession session, @PathVariable Long userId){
        var userExist = Optional.ofNullable(userService.getUserById(userId));
        //var userExist = Optional.ofNullable(userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword()));
        if(userExist.isPresent()){
            session.setAttribute("user", userExist.get());
            return new ResponseEntity<>(userExist.get(), HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @Valid User user){
        var newUser = Optional.ofNullable(userService.getUserById(userId));
        if(newUser.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else if (!newUser.get().equals(user)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else if(newUser.get().getIsDeactivated().equals(user.getIsDeactivated())){
            userService.undoDelete(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        userService.delete(user);
        return ResponseEntity.noContent().build();
    }
}
