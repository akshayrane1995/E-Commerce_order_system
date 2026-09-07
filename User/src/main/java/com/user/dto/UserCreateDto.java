package com.user.dto;

import java.time.LocalDateTime;

public record UserCreateDto(
		Long id, 
		String name, 
		String email, 
		String password,
		String phone, 
		LocalDateTime createdAt,
		LocalDateTime updateAt) {

}
