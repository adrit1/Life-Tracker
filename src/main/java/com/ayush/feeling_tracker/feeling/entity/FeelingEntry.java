package com.ayush.feeling_tracker.feeling.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ayush.feeling_tracker.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "feeling_entries",

		indexes = { @Index(name = "idx_feeling_entry_user", columnList = ("user_id")),
				@Index(name = "idx_feeling_entry_date", columnList = ("date")) },

		uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "date", "entry_hour" }) }

)
public class FeelingEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 120)
	private String message;

	@NotNull
	@Column(nullable = false)
	private LocalDate date;

	@NotNull
	@Min(0)
	@Max(23)
	@Column(name = "hour", nullable = false)
	private Integer hour;

	@NotNull
	@Column(nullable = false)
	@Min(0)
	@Max(5)
	private Integer rating;

	@NotNull
	@Column(nullable = false)
	private Boolean isSleeping;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "feelingEntry", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FeelingMetric> feelingMetrics = new ArrayList<>();

	
	@NotNull
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@NotNull
	@Column(nullable = false)
	private Instant updatedAt;

	// Bean Life cycle
	@PrePersist
	protected void onCreate() {
		Instant now = Instant.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = Instant.now();
	}

	// Getters and setters
	public Long getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getHour() {
		return this.hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsSleeping() {
		return isSleeping;
	}

	public void setIsSleeping(Boolean isSleeping) {
		this.isSleeping = isSleeping;
	}
	public List<FeelingMetric> getFeelingMetrics() {
		return feelingMetrics;
	}


	public Instant getCreatedAt() {
		return this.createdAt;
	}

	public Instant getUpdatedAt() {
		return this.updatedAt;
	}
}
