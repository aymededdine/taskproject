package com.adeem.task.entity;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class WeekDay {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private DayOfWeek dayOfWeek;
	
	
	@OneToMany(mappedBy = "weekDay", cascade = { CascadeType.ALL })
	private List<DayTask> dayTasks;
	
	@ManyToOne
	private WeekTable weekTable;
	
	public List<String> getNamesOfTasks(){
		List<String> list = new ArrayList();
		for (DayTask dayTask : dayTasks) {
			list.add(dayTask.getName());
		}
		return list;
	}
	
	@ManyToOne
	private User user;
	
	


}
