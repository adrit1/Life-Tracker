package com.ayush.feeling_tracker.feeling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.feeling_tracker.feeling.DTO.common.ApiResponse;
import com.ayush.feeling_tracker.feeling.DTO.request.FeelingRequestDTO;
import com.ayush.feeling_tracker.feeling.service.FeelingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/feelings/")
public class FeelingController {
	private final Long tempUserId=1L;
	private final FeelingService feelingService;
	public FeelingController(FeelingService feelingService){
		this.feelingService=feelingService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<String>> createFeeling(@Valid @RequestBody FeelingRequestDTO dto) {
		String res=feelingService.createCustomFeelings(tempUserId, dto.getTitle(), dto.getCategoryId());
		return ResponseEntity.ok(ApiResponse.success(res));
	}
	
	@PutMapping("{feelingId}")
	public ResponseEntity<ApiResponse<String>> updateFeeling(@PathVariable Long feelingId,@Valid @RequestBody FeelingRequestDTO dto) {
		String res=feelingService.updateFeeling(tempUserId, feelingId, dto);
		return ResponseEntity.ok(ApiResponse.success(res));
	}
	
	@DeleteMapping("{feelingId}")
	public ResponseEntity<ApiResponse<String>> deleteFeeling(@PathVariable Long feelingId) {
		String res=feelingService.removeFeeling(tempUserId, feelingId);
		return ResponseEntity.ok(ApiResponse.success(res));
	}
	
	
}
