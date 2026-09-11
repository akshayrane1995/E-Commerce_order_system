package com.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.user.dto.UserCreateDto;
import com.user.dto.UserDto;
import com.user.exception.ResourceNotFoundException;
import com.user.service.UserService;

import jakarta.servlet.ServletException;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void create_shouldReturn201AndUserDto() throws Exception{
		LocalDateTime now = LocalDateTime.now();
		UserCreateDto requestDto = new UserCreateDto("Rahul", "rahul@test.com", "pass123", "9999999999"); 
		UserDto responseDto = new UserDto(1L, "Rahul", "rahul@test.com", "9999999999", null, null);
		
		when(userService.createUser(any(UserCreateDto.class))).thenReturn(responseDto);
		
		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1L))
			.andExpect(jsonPath("$.name").value("Rahul"))
			.andExpect(jsonPath("$.email").value("rahul@test.com"));	
	}
	
	@Test
	void getUserById_shouldReturn200AndUserDto() throws Exception {
	    LocalDateTime now = LocalDateTime.now();
	    UserDto responseDto = new UserDto(1L, "Rahul", "rahul@test.com", "9999999999", now, now);

	    when(userService.getUserById(1L)).thenReturn(responseDto);

	    mockMvc.perform(get("/user/id/{id}", 1L))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(1L))
	        .andExpect(jsonPath("$.name").value("Rahul"))
	        .andExpect(jsonPath("$.email").value("rahul@test.com"));
	}

	@Test
	void getUserById_shouldReturn404_whenUserNotFound() throws Exception {
	    when(userService.getUserById(99L)).thenThrow(new ResourceNotFoundException("user does not exits"));

	    mockMvc.perform(get("/user/id/{id}", 99L))
	        .andExpect(status().isNotFound())
	        .andExpect(jsonPath("$.message").value("user does not exits"))
	        .andExpect(jsonPath("$.errorCode").value("USER_RESOURCE_NOT_FOUND"));
	}
	
	@Test
	void getAllUsers_shouldReturn200AndListOfUsers() throws Exception {
	    LocalDateTime now = LocalDateTime.now();
	    UserDto user1 = new UserDto(1L, "Rahul", "rahul@test.com", "9999999999", now, now);
	    UserDto user2 = new UserDto(2L, "Aman", "aman@test.com", "8888888888", now, now);

	    when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

	    mockMvc.perform(get("/user"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.length()").value(2))
	        .andExpect(jsonPath("$[0].name").value("Rahul"))
	        .andExpect(jsonPath("$[1].name").value("Aman"));
	}

	@Test
	void getAllUsers_shouldReturn200AndEmptyList_whenNoUsers() throws Exception {
	    when(userService.getAllUsers()).thenReturn(List.of());

	    mockMvc.perform(get("/user"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.length()").value(0));
	}
	
	@Test
	void updateUser_shouldReturn200AndUpdatedUserDto() throws Exception {
	    LocalDateTime now = LocalDateTime.now();
	    UserDto requestDto = new UserDto(1L, "Rahul Updated", "rahulupdated@test.com", "8888888888", now, now);
	    UserDto responseDto = new UserDto(1L, "Rahul Updated", "rahulupdated@test.com", "8888888888", now, now);

	    when(userService.updateUser(any(UserDto.class), eq(1L))).thenReturn(responseDto);

	    mockMvc.perform(put("/user/{id}/update", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(requestDto)))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.name").value("Rahul Updated"))
	        .andExpect(jsonPath("$.email").value("rahulupdated@test.com"));
	}
}
