package com.user.mapper;

import com.user.dto.UserDto;
import com.user.entity.User;

public class UserMapper {

	public static User mapToUser(UserDto userDto) {
		User user = new User(
				userDto.id(),
				userDto.name(),
				userDto.email(),
				null,
				userDto.phone(),
				userDto.createdAt(),
				userDto.updateAt());
		
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
