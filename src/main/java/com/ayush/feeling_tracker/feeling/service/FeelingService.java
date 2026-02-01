package com.ayush.feeling_tracker.feeling.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ayush.feeling_tracker.common.exception.BadRequestException;
import com.ayush.feeling_tracker.common.exception.ConflictException;
import com.ayush.feeling_tracker.common.exception.NotFoundException;
import com.ayush.feeling_tracker.feeling.DTO.request.FeelingRequestDTO;
import com.ayush.feeling_tracker.feeling.entity.Feeling;
import com.ayush.feeling_tracker.feeling.entity.FeelingCategory;
import com.ayush.feeling_tracker.feeling.repository.FeelingCategoryRepository;
import com.ayush.feeling_tracker.feeling.repository.FeelingRepository;
import com.ayush.feeling_tracker.user.User;
import com.ayush.feeling_tracker.user.UserService;

import jakarta.transaction.Transactional;

@Service
public class FeelingService {
	private final FeelingRepository feelingRepository;
	private final UserService userService;
	private final FeelingCategoryRepository feelingCategoryRepository;

	public FeelingService(FeelingRepository feelingRepository, UserService userService,
			FeelingCategoryRepository feelingCategoryRepository) {
		this.feelingRepository = feelingRepository;
		this.userService = userService;
		this.feelingCategoryRepository = feelingCategoryRepository;
	}

	@Transactional
	public String createCustomFeelings(Long userId, String title, Long categoryId) {
		User user = userService.getFullUserById(userId);
		// validate if feeling already exists or not
		if (feelingRepository.existsByNameIgnoreCaseAndUser(title, user)
				|| feelingRepository.existsByNameIgnoreCaseAndUserIsNull(title)) {
			throw new ConflictException("Feeling already exists with  this name.");
		}
		FeelingCategory feelingCategory = feelingCategoryRepository.findById(categoryId).orElseThrow(
				() -> new NotFoundException("Feeling Category not found with categoryId=" + categoryId));
		Feeling feeling = new Feeling();
		feeling.setUser(user);
		feeling.setName(title);
		feeling.setCategory(feelingCategory);
		feelingRepository.save(feeling);
		return "Feeling created successfully";

	}

	// read
	public List<Feeling> getAllFeelingCsForAUser(Long userId) {
		User user = userService.getFullUserById(userId);
		return feelingRepository.findAllByUserOrUserIsNull(user);
	}

	// update
	@Transactional
	public String updateFeeling(Long userId, Long feelingId, FeelingRequestDTO dto) {
		User user = userService.getFullUserById(userId);
		Feeling feeling = feelingRepository.findById(feelingId).orElseThrow(
				() -> new NotFoundException("Feeling  not found with feelingId=" + feelingId));
		if (!user.equals(feeling.getUser())) {
			throw new BadRequestException("You can't update system/other users feeling");
		}
		Long feelingCategoryId=dto.getCategoryId();
		feeling.setName(dto.getTitle());
		FeelingCategory feelingCategory=feelingCategoryRepository.findById(feelingCategoryId).orElseThrow(
				() -> new NotFoundException("Feeling category not found with feelingCategoryId=" + feelingCategoryId));
		feeling.setCategory(feelingCategory);
		feelingRepository.save(feeling);
		
		return "Feeling updated successfully";
	}

	// delete - feeling metrics
	@Transactional
	public String removeFeeling(Long userId, Long feelingId) {
		User user = userService.getFullUserById(userId);
		Feeling feeling = feelingRepository.findById(feelingId).orElseThrow(
				() -> new NotFoundException("Feeling  not found with feelingId=" + feelingId));
		if (!user.equals(feeling.getUser())) {
			throw new BadRequestException("You can't remove system/other users feeling");
		}

		feelingRepository.deleteById(feelingId);
		return "Feeling Deleted successfully";
	}
}
