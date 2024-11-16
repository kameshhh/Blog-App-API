package com.project.blog.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceAlreadyPresentException;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.UserDto;
import com.project.blog.repo.UserRepo;
import com.project.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		Optional<User> existingUser = this.userRepo.findByUsername(user.getUsername());
		if(existingUser.isPresent()) {
			throw new ResourceAlreadyPresentException("User", "username", user.getUsername());
		}
		
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Optional<User> existingUser = this.userRepo.findByUsername(userDto.getUsername());
		
		if (existingUser.isPresent() && !Objects.equals(existingUser.get().getId(), userId)) {
		    throw new ResourceAlreadyPresentException("User", "username", userDto.getUsername());
		}
		
		user.setUsername(userDto.getUsername());
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		User updatedUser =  this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user =  this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		return this.userToDto(user);
	}
	
	@Override
	public UserDto getUserByUsername(String username) {
		User user = this.userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "Username", Integer.parseInt(username)));
		return this.userToDto(user);
	}


	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> allUsers = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return allUsers;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user =  this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
	
	public User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
	}
	
	public UserDto userToDto(User userDto) {
        return this.modelMapper.map(userDto, UserDto.class);
	}
}
