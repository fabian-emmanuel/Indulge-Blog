package com.codewithfibbee.blog_api.config;

import com.codewithfibbee.blog_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfig {
    private final UserService userService;

    @Autowired
    public SchedulerConfig(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRate = 6000000L)
    public void ScheduleTaskWithFixedRate(){
        userService.deactivateUserScheduler();
    }
}
