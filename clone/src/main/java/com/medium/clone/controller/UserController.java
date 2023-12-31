package com.medium.clone.controller;

import com.medium.clone.requestDto.UserRequest;
import com.medium.clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public String createUser(@RequestBody UserRequest user) {

        System.out.println("aloo");
        System.out.println(user);
        userService.createUser(user);
        return "user created successfully";
    }

}
