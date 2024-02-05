package com.medium.clone.requestDto;

import lombok.Data;

@Data
public class UserRequest {

    private Integer id;

    private String username;

    private String email;

    private String password;

    private String bio;

    private String img;

    private String token;
}
