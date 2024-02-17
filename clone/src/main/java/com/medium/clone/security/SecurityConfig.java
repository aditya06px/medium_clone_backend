package com.medium.clone.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private  final  CustomerUserDetailsService customUserDetailsService;
    private final   JwtAuthenticationFilter jwtAuthorizationFilter;

    // not used for current flow
//    private final    CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->

                        auth.requestMatchers("/api/users" , "/api/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/profiles/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/articles/*/comments").permitAll()
                                .anyRequest().authenticated()

                )
                // to not use the default jsession id that spring boot provides
                // make it stateless for rest architecture
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // when req comes intercept it and validate jwt token
                // UsernamePasswordAuthenticationFilter is the filter which check user is authentic or not
                // so add filter before it
                .addFilterBefore(jwtAuthorizationFilter , UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults()).build();
    }


      // customize AuthenticationManager to use your custom UserDetailsService (customUserDetailsService in this case)
      // as well as passwordEncoder of your choice
      // by doing this you need to only write custom UserDetailsService and rest will be taken care by spring

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


    // or you can create your own Authentication Provide and write custom logic
    //
//    @Bean
//    public AuthenticationManager authenticationManagerBean() {
//        return new ProviderManager(Arrays.asList(customAuthenticationProvider));
//     }

}