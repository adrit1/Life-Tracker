package com.ayush.feeling_tracker.feeling.entity;

import java.time.Instant;

import com.ayush.feeling_tracker.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="feelings",

indexes = {
		@Index(name="idx_feeling_user",columnList=("user_id")),
		@Index(name="idx_feeling_category",columnList=("category_id"))
},
uniqueConstraints= {
		@UniqueConstraint(columnNames={"name","user_id"})
})
public class Feeling {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable=false,length=50)
	private String name;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id",nullable=false)
	private FeelingCategory category;
	
	//allow null for system feelings and not null for user created feelings
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=true)
	private User user;
	
	@NotNull
	@Column(nullable=false,updatable=false)
	private Instant createdAt;
	
	@NotNull
	@Column(nullable=false)
	private Instant updatedAt;
	
	//Bean Life cycle
	@PrePersist
	protected void onCreate() {
		Instant now=Instant.now();
		this.createdAt=now;
		this.updatedAt=now;
	}
	
	@PreUpdate
	protected void onUpdate() {
		Instant now=Instant.now();
		this.updatedAt=now;
	}
	
	//Getters and setters
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
	
	public FeelingCategory getCategory() {
		return this.category;
	}
	public void setCategory(FeelingCategory category) {
		this.category=category;
	}
	
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user=user;
	}
	
	public Instant getCreatedAt() {
		return this.createdAt;
	}
	public Instant getUpdatedAt() {
		return this.updatedAt;
	}
}
