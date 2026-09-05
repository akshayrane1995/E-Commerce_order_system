package com.user.dto;

import java.time.LocalDateTime;

public record UserDto(Long id, 
					String name, 
					String email, 
					String phone, 
					LocalDateTime createdAt,
					LocalDateTime updateAt) {
}
