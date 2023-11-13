package com.adeem.task.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	private User user;

	private Priority priority;
	
	private Status status;
	

	public enum Status {
		TO_DO, DONE, WITHDRAWN
	}

	public enum Priority {
		HIGH, MEDIUM, LOW
	}

}
