package com.medium.clone.controller;

import com.medium.clone.exception.ResourceNotFoundException;
import com.medium.clone.requestDto.UserRequest;
import com.medium.clone.responseDto.ApiResponse;
import com.medium.clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // should we use ResponseEntity Instead of ApiResponse
    @GetMapping("/{userId}")
    public ApiResponse<?> getUser(@PathVariable Integer userId) throws ResourceNotFoundException {

//        Assert.notNull(userId, "User Id cannot be null or Empty");

        if(userId == null) {
            throw new IllegalArgumentException("User Id cannot be null");
        }

        ApiResponse<?> response = ApiResponse.builder()
                                .data(userService.getUserById(userId))
                .message("Response sent successfully")
                .statusCode(HttpStatus.OK)
                .success(true)
                .build();

        return response;
    }

    // which is better approach for update
    // 1. DTO with Non-Null Values:
    // 2. Reflection-Based Approach:
    // 3. Manual Field-By-Field Checking:
    @PutMapping("/user")
    public ApiResponse<?> updateUser(@RequestBody UserRequest userRequest) throws ResourceNotFoundException {

        return ApiResponse.builder()
                .data(userService.updateUser(userRequest))
                .message("User updated Successfully")
                .statusCode(HttpStatus.OK)
                .build();
    }

    @PostMapping("/api/profiles/{username}/follow")
    public ApiResponse<?> followUser(@PathVariable String username) throws ResourceNotFoundException {
            Integer id = 1; // get from token

        return ApiResponse.builder()
                .data(userService.followUser(username, id))
                .message("user 1 followed " + username)
                .statusCode(HttpStatus.OK)
                .build();
    }


}
