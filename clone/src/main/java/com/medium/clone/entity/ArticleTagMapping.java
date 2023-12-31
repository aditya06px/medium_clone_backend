package com.medium.clone.entity;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "article_tags")
public class ArticleTagMapping {

    @EmbeddedId
    private ArticleTagMappingKey id;

}
