package com.ayush.feeling_tracker.feeling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ayush.feeling_tracker.feeling.entity.FeelingCategory;
import com.ayush.feeling_tracker.user.User;

public interface FeelingCategoryRepository extends JpaRepository<FeelingCategory, Long> {
	@Query("""
		    SELECT DISTINCT fc
		    FROM FeelingCategory fc
		    LEFT JOIN FETCH fc.feeling f
		    WHERE (fc.user = :user
		      OR fc.user IS NULL) AND 
		      (f.user=:user OR f.user IS NULL)
		""")
	List<FeelingCategory> findFeelingCategoryWithFeeling(User user);
	boolean existsByNameIgnoreCaseAndUser(String name,User user);
	boolean existsByNameIgnoreCaseAndUserIsNull(String name);
	List<FeelingCategory> findAllByUserOrUserIsNull(User user);
	
}
