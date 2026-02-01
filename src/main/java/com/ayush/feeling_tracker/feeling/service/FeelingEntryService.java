package com.ayush.feeling_tracker.feeling.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ayush.feeling_tracker.common.exception.BadRequestException;
import com.ayush.feeling_tracker.common.exception.NotFoundException;
import com.ayush.feeling_tracker.feeling.DTO.request.FeelingEntryRequestDTO;
import com.ayush.feeling_tracker.feeling.DTO.request.FeelingMetricRequestDTO;
import com.ayush.feeling_tracker.feeling.DTO.response.DailyFeelingEntriesResponseDTO;
import com.ayush.feeling_tracker.feeling.DTO.response.FeelingEntryResponseDTO;
import com.ayush.feeling_tracker.feeling.DTO.response.FeelingMetricResponseDTO;
import com.ayush.feeling_tracker.feeling.entity.Feeling;
import com.ayush.feeling_tracker.feeling.entity.FeelingEntry;
import com.ayush.feeling_tracker.feeling.entity.FeelingMetric;
import com.ayush.feeling_tracker.feeling.repository.FeelingEntryRepository;
import com.ayush.feeling_tracker.feeling.repository.FeelingMetricRepository;
import com.ayush.feeling_tracker.feeling.repository.FeelingRepository;
import com.ayush.feeling_tracker.user.User;
import com.ayush.feeling_tracker.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FeelingEntryService {
	private static final int MAX_METRICS_PER_ENTRY=3;
	private final UserRepository userRepository;
	private final FeelingEntryRepository feelingEntryRepository;
	private final FeelingMetricRepository feelingMetricRepository;
	private final FeelingRepository feelingRepository;

	public FeelingEntryService(UserRepository userRepository, FeelingEntryRepository feelingEntryRepository,
			FeelingMetricRepository feelingMetricRepository, FeelingRepository feelingRepository) {
		this.userRepository = userRepository;
		this.feelingEntryRepository = feelingEntryRepository;
		this.feelingMetricRepository = feelingMetricRepository;
		this.feelingRepository = feelingRepository;
	}

	// Save or Update Feeling entry
	@Transactional
	public String saveOrUpdateEntry(Long userId,FeelingEntryRequestDTO request) {
		User user=findUser(userId);
		validateEntryTime(user,request.getDate(),request.getHour());
		FeelingEntry entry=findOrCreateEntry(user,request.getDate(),request.getHour());;
		applyEntryState(entry,request.getMessage(),request.getIsSleeping(),request.getRating());
		handleFeelingMetrics(user,entry,request.getFeelingMetrics(),request.getIsSleeping());
		return "Feeling entries saved successfully for date - "+request.getDate()+" and hour - "+request.getHour();
		
	}
	
	//get daily Entries
	public DailyFeelingEntriesResponseDTO getDailyEntries(Long userId,LocalDate date) {
		User user=findUser(userId);
		List<FeelingEntry>entries=feelingEntryRepository.findEntriesWithMetrics(user, date);
		List<FeelingEntryResponseDTO>entryDTO=entries.stream().map(this::mapEntry).toList();
		
		DailyFeelingEntriesResponseDTO res=new DailyFeelingEntriesResponseDTO();
		res.setDate(date);
		res.setEntries(entryDTO);
		return res;
	}
	
	//get feeling entries for a user for a specific day
	public List<FeelingEntry> getFeelingEntriesForADay(Long userId,LocalDate date) {
		User user=findUser(userId);
		List<FeelingEntry>entries=feelingEntryRepository.findAllByUserAndDateOrderByHourAsc(user, date);
		return entries;
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found with userId=" + userId));

	}

	private void validateEntryTime(User user, LocalDate date, Integer hour) {
		LocalDateTime today = LocalDateTime.now(ZoneId.of(user.getTimezone()));
		LocalDateTime entryTime=LocalDateTime.of(date, LocalTime.of(hour, 0));
		System.out.println(today +" Entry time - "+entryTime);

		if (entryTime.isAfter(today)) {
			throw new BadRequestException("Date or hour can't be in future");
		}
	}

	private FeelingEntry findOrCreateEntry(User user, LocalDate date, Integer hour) {
		return feelingEntryRepository.findByUserAndDateAndHour(user, date, hour).orElseGet(() -> {
			FeelingEntry entry = new FeelingEntry();
			entry.setUser(user);
			entry.setDate(date);
			entry.setHour(hour);
			return entry;
		});
	}

	private void applyEntryState(FeelingEntry entry, String message, Boolean isSleeping,Integer rating) {
		entry.setMessage(message);
		entry.setIsSleeping(isSleeping);
		entry.setRating(rating);
		feelingEntryRepository.save(entry);
	}

	private void handleFeelingMetrics(User user, FeelingEntry entry, List<FeelingMetricRequestDTO> feelingMetrics,Boolean isSleeping) {
		if(Boolean.TRUE.equals(isSleeping)) {
			
			feelingMetricRepository.deleteByFeelingEntry(entry);
	        return;
		}
		
		//delete old metrics(update time)
		feelingMetricRepository.deleteByFeelingEntry(entry);
		int noOfFeelings=feelingMetrics.size();
		if(feelingMetrics==null || noOfFeelings==0 || noOfFeelings>MAX_METRICS_PER_ENTRY) {
			throw new BadRequestException(
			        "You must provide between 1 and " + MAX_METRICS_PER_ENTRY + " feelings");		}
		Set<Long> enteredFeelingIds = new HashSet<>();
		for (FeelingMetricRequestDTO feelingMetricRequestDTO : feelingMetrics) {
			if (feelingMetricRequestDTO.getIntensity() < 0 || feelingMetricRequestDTO.getIntensity() > 5) {
				throw new BadRequestException("Metric value must be between 0 and 5");
			}
			if (!enteredFeelingIds.add(feelingMetricRequestDTO.getFeelingId())) {
				throw new BadRequestException("Duplicate feeling in same hour");
			}

			// check if the feeling is system feelings or user's own created feeling
			Feeling feeling = this.feelingRepository.findById(feelingMetricRequestDTO.getFeelingId())
					.orElseThrow(() -> new NotFoundException("Feeling not found"));
			if (feeling.getUser() != null && !feeling.getUser().equals(user)) {
				throw new BadRequestException("Feeling does not belong to user");
			}

			FeelingMetric feelingMetric = new FeelingMetric();
			feelingMetric.setFeeling(feeling);
			feelingMetric.setFeelingEntry(entry);
			feelingMetric.setValue(feelingMetricRequestDTO.getIntensity());

			feelingMetricRepository.save(feelingMetric);

		}
	}
	
	 private FeelingEntryResponseDTO mapEntry(FeelingEntry entry) {

	        FeelingEntryResponseDTO dto = new FeelingEntryResponseDTO();
	        dto.setHour(entry.getHour());
	        dto.setIsSleeping(entry.getIsSleeping());
	        dto.setRating(entry.getRating());
	        dto.setMessage(entry.getMessage());
	        dto.setDate(entry.getDate());
	        if (Boolean.TRUE.equals(entry.getIsSleeping())) {
	            dto.setFeelingMetrics(List.of());
	            return dto;
	        }

	        List<FeelingMetricResponseDTO> metrics =
	                entry.getFeelingMetrics().stream()
	                        .map(metric -> {
	                            FeelingMetricResponseDTO m = new FeelingMetricResponseDTO();
	                            m.setFeelingId(metric.getFeeling().getId());
	                            m.setFeelingName(metric.getFeeling().getName());
	                            m.setIntensity(metric.getValue());
	                            return m;
	                        })
	                        .toList();

	        dto.setFeelingMetrics(metrics);
	        return dto;
	    }

	
	

}
