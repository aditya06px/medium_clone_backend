package com.medium.clone.repository;

import com.medium.clone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity save(UserEntity user);

    boolean existsByUsernameOrEmail(String username, String email);
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(Integer userId);

    Optional<UserEntity> findByUsername(String username);
}
