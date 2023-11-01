package com.adeem.task.config;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adeem.task.entity.Task;
import com.adeem.task.entity.TaskPriority;
import com.adeem.task.entity.TaskPriority.Priority;
import com.adeem.task.entity.TaskStatus;
import com.adeem.task.entity.TaskStatus.Status;
import com.adeem.task.entity.WeekTable;
import com.adeem.task.repository.PriorityRepository;
import com.adeem.task.repository.StatusRepository;
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
	StatusRepository statusRepo;

	@Autowired
	PriorityRepository priorityRepo;

	@Autowired
	WeekTableRepository weekTableRepository;

	@Override
	public void run(String... args) throws Exception {

		if (taskService.listAll().isEmpty()) {

			TaskPriority high = new TaskPriority();
			high.setId(1L);
			high.setPriority(Priority.HIGH);

			TaskPriority medium = new TaskPriority();
			medium.setId(2L);
			medium.setPriority(Priority.MEDIUM);

			TaskPriority low = new TaskPriority();
			low.setId(3L);
			low.setPriority(Priority.LOW);

			TaskStatus todo = new TaskStatus();
			todo.setId(1L);
			todo.setStatus(Status.TO_DO);

			TaskStatus done = new TaskStatus();
			done.setId(2L);
			done.setStatus(Status.DONE);

			high = priorityRepo.save(high);
			medium = priorityRepo.save(medium);
			low = priorityRepo.save(low);
			todo = statusRepo.save(todo);
			done = statusRepo.save(done);

			Task task1 = new Task();
			task1.setName("Quran");
			task1.setPriority(high);
			task1.setStatus(todo);
			// taskRepo.save(task1);

			Task task2 = new Task();
			task2.setName("Sport");
			task2.setPriority(low);
			task2.setStatus(done);
			// taskRepo.save(task2);

			Task task3 = new Task();
			task3.setName("Meeting");
			task3.setPriority(medium);
			task1.setStatus(todo);
			//taskRepo.save(task3);

//			DayOfWeek daysOfWeek = DayOfWeek.MONDAY;
//			
//			WeekTable weekTable = new WeekTable();
//			weekTable.setDaysOfWeek(daysOfWeek);
//			weekTable.setTasks(Arrays.asList(task1, task2, task3));
//			
//
//			ArrayList<DayOfWeek> daysOfWeek2 = new ArrayList<>();
//			daysOfWeek2.add(DayOfWeek.MONDAY);
//			daysOfWeek2.add(DayOfWeek.TUESDAY);
//			daysOfWeek2.add(DayOfWeek.WEDNESDAY);
//			daysOfWeek2.add(DayOfWeek.THURSDAY);
//			daysOfWeek2.add(DayOfWeek.SATURDAY);
//			WeekTable weekTable2 = new WeekTable();
//			weekTable2.setDaysOfWeek(daysOfWeek2);
//			weekTable2.setTasks(Arrays.asList(task1, task2, task3));
//			
//			weekTableRepository.save(weekTable);
//			//weekTableRepository.save(weekTable2);

			taskRepo.save(task1);
			taskRepo.save(task2);
			taskRepo.save(task3);

		}

	}

}
