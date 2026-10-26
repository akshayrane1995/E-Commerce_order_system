package com.user.service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.user.dto.JwtRequest;
import com.user.dto.JwtResponse;
import com.user.entity.User;
import com.user.jwt.JwtAuthenticationHelper;
import com.user.repository.UserRepository;

@Service
public class AuthService {

    private final AuthenticationManager manager;
    private final JwtAuthenticationHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public AuthService(
            AuthenticationManager manager,
            JwtAuthenticationHelper jwtHelper,
            UserDetailsService userDetailsService,
            UserRepository userRepository) {
        this.manager = manager;
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    public JwtResponse login(JwtRequest jwtRequest) {

        doAuthenticate(
            jwtRequest.getEmail(),
            jwtRequest.getPassword()
        );

        UserDetails userDetails =
            userDetailsService.loadUserByUsername(jwtRequest.getEmail());

        String token = jwtHelper.generateToken(userDetails);

        User user = userRepository.findByEmail(jwtRequest.getEmail())
            .orElseThrow(() ->
                new BadCredentialsException("User not found"));

        return JwtResponse.builder()
            .jwtToken(token)
            .userId(user.getId())
            .build();
    }

    private void doAuthenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);

        manager.authenticate(authenticationToken);
    }
}
