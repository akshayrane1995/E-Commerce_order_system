package com.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.user.dto.UserCreateDto;
import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	private UserCreateDto userCreateDto;
	private User user;

	@BeforeEach
	void setup() {
		LocalDateTime now = LocalDateTime.now();
		userCreateDto = new UserCreateDto(null, "Rahul", "rahul@test.com", "pass123", "9999999999", now, now);
		user = new User(1L, "Rahul", "rahul@test.com", "pass123", "9999999999", now, now);
	}

	@Test
	void createUser_shouldReturnSavedUserDto() {
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserDto result = userServiceImpl.createUser(userCreateDto);

		assertNotNull(result);
		assertEquals(1L, result.id());
		assertEquals("Rahul", result.name());
		assertEquals("rahul@test.com", result.email());
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void getUserById_shouldReturnUserDto_whenUserExists() {

		when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

		UserDto result = userServiceImpl.getUserById(1L);

		assertNotNull(result);
		assertEquals(1L, result.id());
		assertEquals("Rahul", result.name());
		verify(userRepository, times(1)).findById(1L);
	}

	@Test
	void getUserById_shouldThrowException_whenUserNotFound() {
		when(userRepository.findById(99L)).thenReturn(java.util.Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> userServiceImpl.getUserById(99L));

		assertEquals("user does not exits", exception.getMessage());
		verify(userRepository, times(1)).findById(99L);
	}

	@Test
	void getAllUsers_shouldReturnListOfUserDto() {
		User user2 = new User(2L, "Aman", "aman@test.com", "pass456", "8888888888", LocalDateTime.now(),
				LocalDateTime.now());
		when(userRepository.findAll()).thenReturn(List.of(user, user2));

		List<UserDto> result = userServiceImpl.getAllUsers();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Rahul", result.get(0).name());
		assertEquals("Aman", result.get(1).name());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void getAllUsers_shouldReturnEmptyList_whenNoUsersExist() {
		when(userRepository.findAll()).thenReturn(List.of());

		List<UserDto> result = userServiceImpl.getAllUsers();

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void updateUser_shouldReturnUpdatedUserDto_whenUserExists() {
		LocalDateTime now = LocalDateTime.now();
		UserDto updateRequest = new UserDto(1L, "Rahul Updated", "rahulupdated@test.com", "8888888888", now, now);

		when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserDto result = userServiceImpl.updateUser(updateRequest, 1L);

		assertNotNull(result);
		verify(userRepository, times(1)).findById(1L);
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void updateUser_shouldThrowException_whenUserNotFound() {
		LocalDateTime now = LocalDateTime.now();
		UserDto updateRequest = new UserDto(99L, "Test", "test@test.com", "7777777777", now, now);

		when(userRepository.findById(99L)).thenReturn(java.util.Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> userServiceImpl.updateUser(updateRequest, 99L));

		assertEquals("User does not exist", exception.getMessage());
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void deleteUser_shouldDeleteSuccessfully_whenUserExists() {
		when(userRepository.existsById(1L)).thenReturn(true);

		userServiceImpl.deleteUser(1L);

		verify(userRepository, times(1)).existsById(1L);
		verify(userRepository, times(1)).deleteById(1L);
	}

	@Test
	void deleteUser_shouldThrowException_whenUserNotFound() {
		when(userRepository.existsById(99L)).thenReturn(false);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> userServiceImpl.deleteUser(99L));

		assertEquals("User does not exist", exception.getMessage());
		verify(userRepository, never()).deleteById(anyLong());
	}
}
