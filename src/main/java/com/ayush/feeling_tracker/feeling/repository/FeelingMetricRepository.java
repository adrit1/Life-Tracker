package com.ayush.feeling_tracker.feeling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.feeling_tracker.feeling.entity.FeelingEntry;
import com.ayush.feeling_tracker.feeling.entity.FeelingMetric;

public interface FeelingMetricRepository extends JpaRepository<FeelingMetric, Long> {
	List<FeelingMetric> findAllByFeelingEntry(FeelingEntry feelingEntry);
	void deleteByFeelingEntry(FeelingEntry feelingEntry);
}
