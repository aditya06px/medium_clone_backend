package com.medium.clone.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/* for this project this class is not being used
* instead of writing configuration for AuthenticationManager to use your custom UserDetails service
* you can write your own Authentication provider */

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

         // encoded data will come from frontend

        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        System.out.println("pass without encoding " + password);

        // You can implement your own logic to authenticate the user based on the password
        if (!passwordEncoder.matches(password, user.getPassword())) {

            System.out.println("here stored in db " + user.getPassword() + " user sent password" + password);
            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        /* if given authentication Object is of type UsernamePasswordAuthenticationToken
         * then this class's authenticate method will be invoked
         * returns true -> will invoke authenticate method
         * returns false -> will not invoke authenticate method */
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
