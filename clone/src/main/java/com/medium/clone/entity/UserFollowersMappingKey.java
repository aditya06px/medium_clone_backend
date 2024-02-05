package com.medium.clone.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowersMappingKey {

    @Column(name = "follower_id")
    private Integer followerId;

    @Column(name = "user_id")
    private Integer userId;
}
