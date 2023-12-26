package com.medium.clone.requestDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String userName;

    private String email;
    private String password;
}
