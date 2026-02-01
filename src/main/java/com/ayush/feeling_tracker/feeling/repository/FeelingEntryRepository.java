package com.ayush.feeling_tracker.feeling.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ayush.feeling_tracker.feeling.entity.FeelingEntry;
import com.ayush.feeling_tracker.user.User;

public interface FeelingEntryRepository extends JpaRepository<FeelingEntry, Long> {
	@Query("""
			    SELECT DISTINCT fe
			    FROM FeelingEntry fe
			    LEFT JOIN FETCH fe.feelingMetrics fm
			    LEFT JOIN FETCH fm.feeling
			    WHERE fe.user = :user
			      AND fe.date = :date
			    ORDER BY fe.hour ASC
			""")
	List<FeelingEntry> findEntriesWithMetrics(User user,LocalDate date);
	Optional<FeelingEntry> findByUserAndDateAndHour(User user, LocalDate date, Integer hour);

	List<FeelingEntry> findAllByUserAndDateOrderByHourAsc(User user, LocalDate date);
}
