package com.user.service;

import java.util.List;

import com.user.dto.UserCreateDto;
import com.user.dto.UserDto;

public interface UserService {

	UserDto createUser(UserCreateDto userCreateDto);

	UserDto getUserById(Long id);

	List<UserDto> getAllUsers();

	UserDto updateUser(UserDto userDto, Long id);

	void deleteUser(Long id);

}
