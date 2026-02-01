package com.ayush.feeling_tracker.feeling.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.feeling_tracker.feeling.DTO.common.ApiResponse;
import com.ayush.feeling_tracker.feeling.DTO.request.FeelingCategoryRequestDTO;
import com.ayush.feeling_tracker.feeling.DTO.response.FeelingCategoryDTO;
import com.ayush.feeling_tracker.feeling.service.FeelingCategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/feeling-categories/")
public class FeelingCategoryController {
	private final FeelingCategoryService feelingCategoryService;
	private final Long tempUserId=1L;
	public FeelingCategoryController(FeelingCategoryService feelingCategoryService) {
		this.feelingCategoryService= feelingCategoryService;
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<FeelingCategoryDTO>>> getFeelingCategoryWithFeeling() {
		
		List<FeelingCategoryDTO> categories=feelingCategoryService.findAllCategoryWithFeeling(tempUserId);
		return ResponseEntity.ok(ApiResponse.success("Category with feeligs fatched successfully",categories));
		
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<String>> createCategory(@Valid @RequestBody FeelingCategoryRequestDTO body) {
		String res = feelingCategoryService.createCustomFeelingCategory(tempUserId, body.getTitle());
		return ResponseEntity.ok(ApiResponse.success(res));
	}
	
	@PutMapping("{categoryId}")
	public ResponseEntity<ApiResponse<String>> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody FeelingCategoryRequestDTO body) {
		String res=feelingCategoryService.updateFeelingCategory(tempUserId, categoryId, body.getTitle());
		return ResponseEntity.ok(ApiResponse.success(res));
	}
	
	@DeleteMapping("{categoryId}")
	public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Long categoryId) {
		String res=feelingCategoryService.removeFeelingCategory(tempUserId, categoryId);
		return ResponseEntity.ok(ApiResponse.success(res));
	}
}
