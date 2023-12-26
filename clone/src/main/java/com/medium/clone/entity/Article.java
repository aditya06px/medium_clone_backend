package com.medium.clone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 250)
    private String title;


    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "body", columnDefinition = "text")
    private String body;
    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "fav_count", columnDefinition = "int default 0")
    private Integer favCount;

}
