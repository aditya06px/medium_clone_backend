package com.medium.clone.service.serviceImpl;

import com.medium.clone.entity.UserEntity;
import com.medium.clone.entity.UserFollowersMapping;
import com.medium.clone.exception.ResourceNotFoundException;
import com.medium.clone.repository.UserFollowerMappingRepository;
import com.medium.clone.repository.UserRepository;
import com.medium.clone.requestDto.UserRequest;
import com.medium.clone.responseDto.UserResponseDto;
import com.medium.clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepo;
    private final UserFollowerMappingRepository userFollowerMappingRepo;



    public UserRequest loginUser(UserRequest userRequest) throws ResourceNotFoundException {

       UserEntity user =  userRepo.findByEmail(userRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException("user with given Email Doesn't exists"));;

        UserRequest responseUser = new UserRequest();

       if(user == null || !user.getPassword().equals(userRequest.getPassword())) {
           responseUser.setToken("email or password is wrong");
           return responseUser;
       }

       responseUser = modelMapper.map(user, UserRequest.class);
       responseUser.setToken("successfully login");
       responseUser.setPassword("");
       return responseUser;
    }

    @Override
    public UserResponseDto getUserById(Integer userId) throws ResourceNotFoundException {
        UserEntity userEntity = userRepo.findByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("user with given id Doesn't exists"));


        UserResponseDto userResponse = UserResponseDto.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .bio(userEntity.getBio())
                .image(userEntity.getImage())
                .token("tokenIsNotSetYet")
                .build();


        return userResponse;
    }

    @Override
    public UserResponseDto updateUser(UserRequest userRequest) throws ResourceNotFoundException {
        UserEntity userEntity = userRepo.findById(userRequest.getId()).orElseThrow(()-> new ResourceNotFoundException("user with given id Doesn't exists"));

        userRequest.setId(null);

        if(userRequest.getBio() != null) userEntity.setBio(userRequest.getBio());

        if(userRequest.getImg() != null) userEntity.setImage(userRequest.getImg());

        if(userRequest.getEmail() != null) userEntity.setEmail(userRequest.getEmail());

        if(userRequest.getPassword() != null) userEntity.setPassword(userRequest.getPassword());

        UserEntity updatedUserEntity = userRepo.save(userEntity);

        return modelMapper.map(updatedUserEntity, UserResponseDto.class);
    }

    @Override
    public UserResponseDto followUser(String username, Integer userId) throws ResourceNotFoundException {
        UserEntity userProfile = userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(String.format("user with %s doesn't exist", username)));


        List<UserFollowersMapping> list = userFollowerMappingRepo.checkIfExists(userId, userProfile.getUserId());

        if(list.isEmpty()) {
            userFollowerMappingRepo.followUser(userId, userProfile.getUserId());
            return UserResponseDto.builder()
                    .username(userProfile.getUsername())
                    .bio(userProfile.getBio())
                    .image(userProfile.getImage())
                    .following(true)
                    .build();
        }
        return UserResponseDto.builder()
                .username(userProfile.getUsername())
                .bio(userProfile.getBio())
                .image(userProfile.getImage())
                .following(true)
                .build();

    }
}
