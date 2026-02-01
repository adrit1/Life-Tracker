package com.ayush.feeling_tracker.feeling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.feeling_tracker.feeling.entity.Feeling;
import com.ayush.feeling_tracker.user.User;

public interface FeelingRepository extends JpaRepository<Feeling, Long> {
	boolean existsByNameIgnoreCaseAndUser(String name,User user);
	boolean existsByNameIgnoreCaseAndUserIsNull(String name);
	List<Feeling> findAllByUserOrUserIsNull(User user);
}
