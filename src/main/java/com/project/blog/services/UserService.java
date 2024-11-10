package com.project.blog.services;

import java.util.List;

import com.project.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Integer userID);
	UserDto getUserById(Integer UserId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
