package com.ayush.feeling_tracker.feeling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "feeling_metrics", indexes = {
		@Index(name = "idx_feeling_metric_entry", columnList = ("feeling_entry_id")),
		@Index(name = "idx_feeling_metric_feeling", columnList = ("feeling_id")) },

		uniqueConstraints = { @UniqueConstraint(columnNames = { "feeling_entry_id", "feeling_id" }) })

public class FeelingMetric {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feeling_id", nullable = false)
	private Feeling feeling;

	@NotNull
	@Column(name="intensity",nullable = false)
	@Min(0)
	@Max(5)
	private Integer value;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feeling_entry_id", nullable = false)
	private FeelingEntry feelingEntry;

	// Getters and setters
	public Long getId() {
		return this.id;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Feeling getFeeling() {
		return this.feeling;
	}

	public void setFeeling(Feeling feeling) {
		this.feeling = feeling;
	}

	public FeelingEntry getFeelingEntry() {
		return this.feelingEntry;
	}

	public void setFeelingEntry(FeelingEntry feelingEntry) {
		this.feelingEntry = feelingEntry;
	}

}
