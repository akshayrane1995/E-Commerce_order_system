package com.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dto.UserCreateDto;
import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.mapper.UserMapper;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDto createUser(UserCreateDto userCreateDto) {
		User user = UserMapper.mapToUser(userCreateDto);
		user.setPassword(passwordEncoder.encode(userCreateDto.password()));
		LocalDateTime now = LocalDateTime.now();
	    user.setCreatedAt(now);
	    user.setUpdateAt(now);
		User saveUser = userRepository.save(user);
		return UserMapper.mapToUserDto(saveUser);
	}

	@Override
	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user does not exits"));
		return UserMapper.mapToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> UserMapper.mapToUserDto(user)).collect(Collectors.toList());
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

		user.setName(userDto.name());
		user.setEmail(userDto.email());
		user.setPhone(userDto.phone());
		user.setUpdateAt(LocalDateTime.now());

		User updatedUser = userRepository.save(user);
		return UserMapper.mapToUserDto(updatedUser);
	}

	@Override
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User does not exist");
		}
		userRepository.deleteById(id);
	}

}
