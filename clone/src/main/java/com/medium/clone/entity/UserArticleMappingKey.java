package com.medium.clone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class UserArticleMappingKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "article_id")
    private Long articleId;
}
