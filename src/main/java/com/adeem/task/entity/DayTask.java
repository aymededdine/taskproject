package com.adeem.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class DayTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@Column(name="status")
	private Status status;

	@ManyToOne
	private WeekDay weekDay;
	
	
	public enum Status { 
		TO_DO, DONE, WITHDRAWN 
	}

}
