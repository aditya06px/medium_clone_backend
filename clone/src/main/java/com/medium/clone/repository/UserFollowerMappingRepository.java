package com.medium.clone.repository;

import com.medium.clone.entity.UserFollowersMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserFollowerMappingRepository extends JpaRepository<UserFollowersMapping, Integer> {

    @Modifying
    @Query(value = "INSERT INTO user_follows (user_id, follower_id) VALUES (:userId, :followerId)", nativeQuery = true)
    void followUser(@Param("followerId") Integer followerId, @Param("userId") Integer userId);

    @Query(value = "SELECT * FROM user_follows WHERE user_id = :userId AND follower_id = :followerId", nativeQuery = true)
    List<UserFollowersMapping> checkIfExists( @Param("followerId") Integer followerId, @Param("userId") Integer userId);
}
