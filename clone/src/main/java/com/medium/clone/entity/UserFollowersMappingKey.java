package com.medium.clone.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class UserFollowersMappingKey {

    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "user_id")
    private Long userId;
}
