package com.medium.clone.repository;

import com.medium.clone.entity.UserEntity;
import com.medium.clone.requestDto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    User save(User user);


}
