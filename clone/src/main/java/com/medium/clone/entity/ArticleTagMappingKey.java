package com.medium.clone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ArticleTagMappingKey {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "tag_id")
    private Long tagId;
}
