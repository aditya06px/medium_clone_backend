package com.medium.clone.responseDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserResponseDto {
    private String email;
    private String image;
    private String token;
    private String username;
    private String bio;
    private boolean following;
}
