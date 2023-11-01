package com.adeem.task.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class WeekTable {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "weekTable")
	private List<WeekDay> weekDays;
	
	@Column(name="submitted")
	private boolean submitted = false;
	
	

}
