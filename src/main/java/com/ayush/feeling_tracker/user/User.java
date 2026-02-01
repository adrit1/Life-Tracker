package com.ayush.feeling_tracker.user;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	private String name;
	
	@Email
	@NotBlank
	@Column(nullable=false,updatable=false)
	private String email;
	
	@NotBlank
	@Column(nullable=false)
	private String password;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private UserStatus status;
	
	@Column(nullable=false)
	@NotNull
	private String timezone;
	
	@Column(nullable=false,updatable=false)
	private Instant createdAt;
	
	@Column(nullable=false)
	private Instant updatedAt;
	
	//============ LifeCycle Hook =========
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
	
	//============ Getters Setters =========
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	
	public UserStatus getStatus() {
		return this.status;
	}
	public void setStatus(UserStatus status) {
		this.status=status;
	}
	
	public String getTimezone() {
		return this.timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone=timezone;
	}
	
	public Instant getCreatedAt() {
		return this.createdAt;
	}
	
	public Instant getUpdatedAt() {
		return this.updatedAt;
	}
	
}
