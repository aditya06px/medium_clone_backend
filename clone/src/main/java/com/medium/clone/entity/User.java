package com.medium.clone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 250)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "bio")
    private String bio;

    @Column(name = "google_id")
    private String googleId;

}
