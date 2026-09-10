package com.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.JwtRequest;
import com.user.dto.JwtResponse;
import com.user.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	private AuthService authService;
	
	public AuthController(AuthService authService){
		this.authService = authService;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest jwtRequest) {
		return new ResponseEntity<>(authService.login(jwtRequest),HttpStatus.OK);
	}
}

