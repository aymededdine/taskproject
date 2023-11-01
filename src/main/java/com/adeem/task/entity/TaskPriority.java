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

import lombok.Data;

@Data
@Entity
@Table(name="priority")
public class TaskPriority {
	
	

    

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Priority priority; 
    @OneToMany(mappedBy = "priority") 
    private List<Task> tasks;

    public enum Priority { 
        HIGH, MEDIUM, LOW
    }
}
