package com.adeem.task.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.adeem.task.entity.TaskPriority.Priority;

import lombok.Data;

@Data
@Entity
@Table(name = "status")
public class TaskStatus {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Status status; // Use the enum type here
    @OneToMany(mappedBy = "status") // Use mappedBy to specify the reverse side of the relationship
	private List<Task> tasks;

	public enum Status { 
		TO_DO, DONE
	}

}
