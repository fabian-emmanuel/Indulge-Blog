package com.codewithfibbee.blog_api.service.serviceImpl;

import com.codewithfibbee.blog_api.models.User;
import com.codewithfibbee.blog_api.repositories.UserRepo;
import com.codewithfibbee.blog_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public User getUserByUsername(String userName) {
        return userRepo.findUserByUserName(userName);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findUserById(userId);
    }


    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepo.findUserByEmailAndPassword(email, password);
    }

    @Override
    public void delete(User user) {
        log.info("***** DELETE USER CALLED *****");
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        String presentDate = DateFor.format(calendar.getTime());
        user.setIsDeactivated(true);
        user.setDeactivationDate(presentDate);
        userRepo.save(user);
    }

    @Override
    public void undoDelete(User user) {
        log.info("***** UNDO DELETE CALLED *****");
        user.setIsDeactivated(false);
        user.setDeactivationDate(null);
        userRepo.save(user);
    }

    @Override
    public void deactivateUserScheduler() {
        List<User> users = userRepo.findAllByIsDeactivated(true);
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        log.info("***** SCHEDULER IS RUNNING *****");
        users.forEach(user -> {
            String presentDate = DateFor.format(date);
            String deleteDate = user.getDeactivationDate();
            int deleteAction = presentDate.compareTo(deleteDate);
            if (deleteAction > 0 || deleteAction == 0) {
               user.setIsDeleted(true);
                userRepo.save(user);
            }
        });
    }


}
