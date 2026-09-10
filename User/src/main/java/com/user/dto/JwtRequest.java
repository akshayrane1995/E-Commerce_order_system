package com.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest{
	 	@NotBlank(message = "Email is required")
		String email;
	 	
	 	@NotBlank(message = "Password is required")
		String password;
}
