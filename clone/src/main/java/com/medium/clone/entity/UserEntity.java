package com.medium.clone.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "user_table")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 150)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 250)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "bio")
    private String bio;

    @Column(name = "google_id")
    private String googleId;

}
