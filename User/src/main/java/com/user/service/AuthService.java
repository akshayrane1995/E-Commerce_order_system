package com.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.user.dto.JwtRequest;
import com.user.dto.JwtResponse;
import com.user.jwt.JwtAuthenticationHelper;

@Service
public class AuthService {

	private final AuthenticationManager manager;	
	
	private final JwtAuthenticationHelper jwtHelper;
	
	private final UserDetailsService userDetailsService;

	public AuthService(AuthenticationManager manager, JwtAuthenticationHelper jwtHelper,
			UserDetailsService userDetailsService) {
		this.manager = manager;
		this.jwtHelper = jwtHelper;
		this.userDetailsService = userDetailsService;
	}
	
	public JwtResponse login(JwtRequest jwtRequest) {

		// authenticate with authenticationManager
		this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
		String token = jwtHelper.generateToken(userDetails);
		
		JwtResponse response = JwtResponse.builder().jwtToken(token).build();
		return response;
	}

	private void doAuthenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			manager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid username and password");
		}
	}
}
