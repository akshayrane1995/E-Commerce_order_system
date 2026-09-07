package com.user.mapper;

import com.user.dto.UserCreateDto;
import com.user.dto.UserDto;
import com.user.entity.User;

public class UserMapper {

	public static User mapToUser(UserCreateDto userCreateDto) {
		User user = new User(
				userCreateDto.id(),
				userCreateDto.name(),
				userCreateDto.email(),
				null,
				userCreateDto.phone(),
				userCreateDto.createdAt(),
				userCreateDto.updateAt());
		
		return user;
	}
	
	
	public static UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getPhone(),
				user.getCreatedAt(),
				user.getUpdateAt());
		
		return userDto;
	}
}
