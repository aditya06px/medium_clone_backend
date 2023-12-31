package com.medium.clone.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_fav_articles")
@Data
public class UserArticleMapping {

    @EmbeddedId
    private UserArticleMappingKey id;
}
