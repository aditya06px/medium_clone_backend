package com.medium.clone.service;

import com.medium.clone.exception.ResourceNotFoundException;
import com.medium.clone.requestDto.UserRequest;
import com.medium.clone.responseDto.UserResponseDto;

public interface UserService {

    UserResponseDto getUserById(Integer userId) throws ResourceNotFoundException;

    UserResponseDto updateUser(UserRequest userRequest) throws ResourceNotFoundException;

    UserResponseDto followUser(String username, Integer userId) throws ResourceNotFoundException;
}
