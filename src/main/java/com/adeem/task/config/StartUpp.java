package com.adeem.task.config;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adeem.task.entity.Task;


import com.adeem.task.repository.TaskRepository;
import com.adeem.task.repository.WeekTableRepository;
import com.adeem.task.service.TaskService;

@Component
public class StartUpp implements CommandLineRunner {

	@Autowired
	TaskService taskService;

	@Autowired
	TaskRepository taskRepo;

	@Autowired
	WeekTableRepository weekTableRepository;

	@Override
	public void run(String... args) throws Exception {

		if (taskService.listAll().isEmpty()) {


			Task task1 = new Task();
			task1.setName("Quran");
			task1.setPriority(Task.Priority.HIGH);
			task1.setStatus(Task.Status.TO_DO);

			Task task2 = new Task();
			task2.setName("Sport");
			task2.setPriority(Task.Priority.LOW);
			task2.setStatus(Task.Status.TO_DO);

			Task task3 = new Task();
			task3.setName("Meeting");
			task3.setPriority(Task.Priority.MEDIUM);
			task3.setStatus(Task.Status.DONE);
			
			Task task4 = new Task();
			task4.setName("Test");
			task4.setPriority(Task.Priority.HIGH);
			task4.setStatus(Task.Status.WITHDRAWN);


			taskRepo.save(task1);
			taskRepo.save(task2);
			taskRepo.save(task3);
			taskRepo.save(task4);

		}

	}

}
