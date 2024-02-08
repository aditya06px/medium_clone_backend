package com.medium.clone.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private  final  CustomerUserDetailsService customUserDetailsService;
    private final   JwtAuthenticationFilter jwtAuthorizationFilter;
    private final    CustomAuthenticationProvider customAuthenticationProvider;

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->

                        auth.requestMatchers("/api/users" , "/api/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/profiles/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/articles/*/comments").permitAll()
                                .anyRequest().authenticated()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter , UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults()).build();
    }


//    @Bean
//    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//        return authenticationManagerBuilder.build();
//    }


    // or you can create your own Authentication Provider
    // and write custom logic
    @Bean
    public AuthenticationManager authenticationManagerBean() {
        return new ProviderManager(Arrays.asList(customAuthenticationProvider));
     }

}