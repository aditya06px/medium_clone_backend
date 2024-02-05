package com.medium.clone.security;

import com.medium.clone.entity.UserEntity;
import com.medium.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("inside load user by name method");
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Email "+ username+ "not found"));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

}

