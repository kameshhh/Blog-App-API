package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.UserDto;
import com.project.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	private ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/id/{userId}")
	private ResponseEntity<UserDto> getUserbyId(@PathVariable("userId") Integer uId){
		UserDto userDetails = this.userService.getUserById(uId);
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	@GetMapping("/username/{username}")
	private ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username){
		UserDto userDetails = this.userService.getUserByUsername(username);
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	// Retrieve by user ID or username using query parameters
	@GetMapping("/user")
	private ResponseEntity<UserDto> getUser(
	    @RequestParam(value = "userId", required = false) Integer userId,
	    @RequestParam(value = "username", required = false) String username) {

	    UserDto userDetails;
	    if (userId != null) {
	        userDetails = this.userService.getUserById(userId);
	    } else if (username != null) {
	        userDetails = this.userService.getUserByUsername(username);
	    } else {
	        throw new IllegalArgumentException("Either userId or username must be provided");
	    }
	    return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@GetMapping("/")
	private ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	private ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Integer uId){
		
		UserDto updatedUser = this.userService.updateUser(userDto, uId);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	private ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId){
		this.userService.deleteUser(uId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}

}
