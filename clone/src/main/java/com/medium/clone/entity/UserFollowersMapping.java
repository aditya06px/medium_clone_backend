package com.medium.clone.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "user_follows")
public class UserFollowersMapping {

    @EmbeddedId
    private UserFollowersMappingKey id;
}
