package com.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserCreateDto;
import com.user.dto.UserDto;
import com.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// create user
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
		UserDto save = userService.createUser(userCreateDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);

	}
	
	//get user by id
	@GetMapping("/id/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
		UserDto userDto = userService.getUserById(id);
		return ResponseEntity.ok(userDto);
	}
	
	//Get all user
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	//update user details
	@PutMapping("/{id}/update")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto , @PathVariable Long id){
		UserDto updatedUser = userService.updateUser(userDto,id);
		return ResponseEntity.ok(updatedUser);
	}
	
	//delete user
	@DeleteMapping("/{id}/remove")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted sucessfully");
	}
}
