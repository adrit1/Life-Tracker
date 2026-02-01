package com.ayush.feeling_tracker.feeling.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ayush.feeling_tracker.common.exception.BadRequestException;
import com.ayush.feeling_tracker.common.exception.ConflictException;
import com.ayush.feeling_tracker.common.exception.NotFoundException;
import com.ayush.feeling_tracker.feeling.DTO.response.FeelingCategoryDTO;
import com.ayush.feeling_tracker.feeling.DTO.response.FeelingItemDTO;
import com.ayush.feeling_tracker.feeling.entity.Feeling;
import com.ayush.feeling_tracker.feeling.entity.FeelingCategory;
import com.ayush.feeling_tracker.feeling.repository.FeelingCategoryRepository;
import com.ayush.feeling_tracker.user.User;
import com.ayush.feeling_tracker.user.UserService;

import jakarta.transaction.Transactional;

@Service
public class FeelingCategoryService {
	private final FeelingCategoryRepository feelingCategoryRepository;
	private final UserService userService;

	public FeelingCategoryService(FeelingCategoryRepository feelingCategoryRepository,UserService userService) {
		this.feelingCategoryRepository= feelingCategoryRepository;
		this.userService=userService;
	}
	@Transactional
	public String createCustomFeelingCategory(Long userId,String title) {
		User user=userService.getFullUserById(userId);
		//validate if feeling already exists or not
		if(feelingCategoryRepository.existsByNameIgnoreCaseAndUser(title, user) || feelingCategoryRepository.existsByNameIgnoreCaseAndUserIsNull(title)) {
			throw new ConflictException("Feeling category already exists with  this name.");
		}
		FeelingCategory feelingCategory=new FeelingCategory();
		feelingCategory.setUser(user);
		feelingCategory.setName(title);
		feelingCategoryRepository.save(feelingCategory);
		return "Feeling Category created successfully";
		
	}
	//read
	//List<FeelingAndCategoryResponseDTO>
	public List<FeelingCategoryDTO> findAllCategoryWithFeeling(Long userId){
		User user=userService.getFullUserById(userId);
		List<FeelingCategory> categories=feelingCategoryRepository.findFeelingCategoryWithFeeling(user);
		List<FeelingCategoryDTO>dto=categories.stream().map(this::categoryDTOMapper).toList();
		return dto;
	}
	public List<FeelingCategory> getAllFeelingCategoriesForAUser(Long userId){
		User user=userService.getFullUserById(userId);
		return feelingCategoryRepository.findAllByUserOrUserIsNull(user);
	}
	
	//update
	@Transactional
	public String updateFeelingCategory(Long userId,Long categoryId,String title) {
		User user=userService.getFullUserById(userId);
		FeelingCategory category=feelingCategoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Feeling category not found with categoryId=" + categoryId));
		if(!user.equals(category.getUser())) {
			throw new BadRequestException("You can't update system/other users category" );
		}
		category.setName(title);
		feelingCategoryRepository.save(category);
		return "Category updated successfully";
	}
	
	//delete - all feelings,feeling metrics
	@Transactional
	public String removeFeelingCategory(Long userId,Long categoryId) {
		User user=userService.getFullUserById(userId);
		FeelingCategory category=feelingCategoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Feeling category not found with categoryId=" + categoryId));
		if(!user.equals(category.getUser())) {
			throw new BadRequestException("You can't remove system/other users category" );
		}
		
		feelingCategoryRepository.deleteById(categoryId);
		return "Category Deleted successfully";
	}
	
	
	//helper classes -------- * ----------
	public FeelingCategoryDTO categoryDTOMapper(FeelingCategory feelingCategory) {
		FeelingCategoryDTO dto=new FeelingCategoryDTO();
		dto.setId(feelingCategory.getId());
		dto.setName(feelingCategory.getName());
		
		List<FeelingItemDTO> feelingDtos=feelingCategory.getFeeling().stream().map(this::feelingDTOMapper).toList();
		dto.setFeelings(feelingDtos);
		return dto;
	}
	
	public FeelingItemDTO feelingDTOMapper(Feeling feeling) {
		FeelingItemDTO dto=new FeelingItemDTO();
		dto.setId(feeling.getId());
		dto.setName(feeling.getName());
		return dto;
	}
}
