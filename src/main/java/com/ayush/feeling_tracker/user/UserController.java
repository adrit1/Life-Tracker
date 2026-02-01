package com.ayush.feeling_tracker.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService =userService;
	}
	
	@GetMapping("/{id}")
	public UserResponseDTO getUser(@PathVariable Long id) {
		return userService.getUserById(id);
	}
}
