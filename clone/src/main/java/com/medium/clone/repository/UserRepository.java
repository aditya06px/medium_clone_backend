package com.medium.clone.repository;

import com.medium.clone.entity.User;
import com.medium.clone.requestDto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {


    User save(UserDto user);
//    User hello mf
    User create(UserDto user1);
}
