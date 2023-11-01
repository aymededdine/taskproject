package com.adeem.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.Task;
import com.adeem.task.entity.TaskPriority;
import com.adeem.task.entity.TaskStatus;
import com.adeem.task.repository.PriorityRepository;
import com.adeem.task.repository.StatusRepository;
import com.adeem.task.repository.TaskRepository;

import ch.qos.logback.core.util.StatusPrinter;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	PriorityRepository priorityRepository;
	
	@Autowired
	StatusRepository statusRepository;

	public List<Task> listAll() {
		return taskRepo.findAll();
	}

	public Optional<Task> findById(Long id) {
		return taskRepo.findById(id);
	}

	public Task findByName(String name) {
		return taskRepo.findByName(name);
	}

	public Task insert(Task task) {
		var taskObject = task;
		var x = priorityRepository.findById(task.getPriority().getId()).orElseThrow(() -> new IllegalArgumentException("Priority not found"));
		var y = statusRepository.findById(task.getStatus().getId()).orElseThrow(() -> new IllegalArgumentException("Status not found"));
		task.setPriority(x);
		task.setStatus(y);
		
		return taskRepo.save(task);
	}

	public Task update(Task task) {
		// Use Optional's orElseThrow to provide a custom exception message.
		Task current = taskRepo.findById(task.getId())
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));

		// Update the fields individually.
		current.setName(task.getName());
		current.setPriority(task.getPriority());
		current.setStatus(task.getStatus());

		return taskRepo.save(current);
	}

	public boolean deleteById(Long taskId) {
		if (taskRepo.existsById(taskId)) {
			taskRepo.deleteById(taskId);
			
			return true;
		}
		return false;
	}
}
