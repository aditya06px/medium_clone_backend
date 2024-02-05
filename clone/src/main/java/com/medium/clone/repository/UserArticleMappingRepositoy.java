package com.medium.clone.repository;

import com.medium.clone.entity.UserArticleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArticleMappingRepositoy extends JpaRepository<UserArticleMapping, Integer> {
}
