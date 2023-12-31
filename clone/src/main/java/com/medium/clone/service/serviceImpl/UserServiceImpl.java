package com.medium.clone.service.serviceImpl;

import com.medium.clone.entity.UserEntity;
import com.medium.clone.repository.UserRepository;
import com.medium.clone.requestDto.UserRequest;
import com.medium.clone.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void createUser(UserRequest userRequest) {
        UserEntity newUser =  modelMapper.map(userRequest.getUser(), UserEntity.class);
        System.out.println(newUser);
        userRepo.save(newUser);

    }
}
