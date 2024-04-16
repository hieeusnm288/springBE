package com.example.bespring.security;

import com.example.bespring.filter.JwtFilter;
import com.example.bespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration{
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
//    @Autowired
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                configurer -> configurer
                        .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENDPOINS).permitAll()
                        .requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST_ENDPOINS).permitAll()
                        .requestMatchers(HttpMethod.GET, Endpoints.ADMIN_GET_ENDPOINS).hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, Endpoints.ADMIN_POST_ENDPOINS).hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, Endpoints.ADMIN_PUT_ENDPOINS).hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, Endpoints.ADMIN_DELETE_ENDPOINS).hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, Endpoints.GUEST_GET_ENDPOINS).hasAuthority("guest")
                        .requestMatchers(HttpMethod.POST, Endpoints.GUEST_POST_ENDPOINS).hasAuthority("guest")
        );
    http.cors(cors -> {
        cors.configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.addAllowedOrigin(Endpoints.front_end_host);
            corsConfig.addAllowedMethod("OPTIONS");
            corsConfig.addAllowedMethod("GET");
            corsConfig.addAllowedMethod("POST");
            corsConfig.addAllowedMethod("PUT");
            corsConfig.addAllowedMethod("DELETE");
            corsConfig.addAllowedHeader("*");
            return corsConfig;
        });
    });
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.httpBasic(Customizer.withDefaults());
    http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
