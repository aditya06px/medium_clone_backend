package com.medium.clone.controller;

import com.medium.clone.entity.UserEntity;
import com.medium.clone.exception.ResourceNotFoundException;
import com.medium.clone.repository.UserRepository;
import com.medium.clone.requestDto.UserRequest;
import com.medium.clone.responseDto.ApiResponse;
import com.medium.clone.responseDto.UserResponseDto;
import com.medium.clone.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthenticationController {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @PostMapping
    public ApiResponse<String> createUser(@RequestBody UserRequest user) {

        if (userRepo.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            return ApiResponse.<String>builder()
                    .data("user with given username or email already exist")
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("something went wrong")
                    .success(true)
                    .build();
        }

        UserEntity newUser = modelMapper.map(user, UserEntity.class);

        // will come encrypted password from frontend
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(newUser);

        return ApiResponse.<String>builder()
                .data(String.format("user %s created successfully", user.getUsername()))
                .statusCode(HttpStatus.OK)
                .message("user created successfully")
                .success(true)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<?> loginUser(@RequestBody UserRequest user) throws ResourceNotFoundException {

        // you can write your own authentication manager by implementing AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));


        System.out.println("storing authentication object in contextHolder");
        // SecurityContextHolder is a class in Spring Security that provides access
        // to the security context of the current thread
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        UserEntity userEntity = userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("something went wrong with this username or email"));

        UserResponseDto responseDto = UserResponseDto.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .bio(userEntity.getBio())
                .token(token)
                .image(userEntity.getImage())
                .build();

        return ApiResponse.builder()
                .data(responseDto)
                .statusCode(HttpStatus.OK)
                .message("user created successfully")
                .success(true)
                .build();

    }


}
