package com.medium.clone.repository;

import com.medium.clone.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Long, Article> {
}
