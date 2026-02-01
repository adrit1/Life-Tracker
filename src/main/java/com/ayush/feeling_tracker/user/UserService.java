package com.ayush.feeling_tracker.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	
//	public String createUser()
	public UserResponseDTO getUserById(Long id) {
		User user=userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with userId=" + id));
		return mapToResponse(user);
	}
	public User getFullUserById(Long id) {
		User user=userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with userId=" + id));
		return user;
	}
	private UserResponseDTO mapToResponse(User user) {

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTimezone(user.getTimezone());
        dto.setStatus(user.getStatus().name());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }
}
