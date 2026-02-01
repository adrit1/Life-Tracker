package com.ayush.feeling_tracker.feeling.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.feeling_tracker.feeling.DTO.common.ApiResponse;
import com.ayush.feeling_tracker.feeling.DTO.request.FeelingEntryRequestDTO;
import com.ayush.feeling_tracker.feeling.DTO.response.DailyFeelingEntriesResponseDTO;
import com.ayush.feeling_tracker.feeling.service.FeelingEntryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/feelings-entries")
public class FeelingEntryController {
//	private final UserService userService;
	private final FeelingEntryService feelingEntryService;
	
	@GetMapping("/test")
	public String test() {
		return "Working Backend";
	}
	public FeelingEntryController(FeelingEntryService feelingEntryService) {
		this.feelingEntryService =feelingEntryService;
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<ApiResponse<String>> saveOrUpdateFeelingEntry(@PathVariable Long userId,@Valid @RequestBody FeelingEntryRequestDTO request) {
		System.out.println(request);
		String res=feelingEntryService.saveOrUpdateEntry(userId, request);
		return ResponseEntity.ok(ApiResponse.success(res));
	}
	
	@GetMapping("/day")
	public ResponseEntity<ApiResponse<DailyFeelingEntriesResponseDTO>> getDayEntries(@RequestParam LocalDate date) {
		Long tempUserId=1L;
		DailyFeelingEntriesResponseDTO data=feelingEntryService.getDailyEntries(tempUserId, date);
		return ResponseEntity.ok(ApiResponse.success("All Entries Fetched successfully", data));
	}
	

}
