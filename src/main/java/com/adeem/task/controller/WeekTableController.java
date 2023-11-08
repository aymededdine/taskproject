package com.adeem.task.controller;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.Task;
import com.adeem.task.entity.WeekDay;
import com.adeem.task.entity.WeekTable;
import com.adeem.task.service.DayTaskService;
import com.adeem.task.service.WeekDayService;
import com.adeem.task.service.WeekTableService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/weekly-tasks")
public class WeekTableController {

	private final WeekTableService weekTableService;

	@Autowired
	WeekDayService weekDayService;

	@Autowired
	DayTaskService dayTaskService;

	@Autowired
	public WeekTableController(WeekTableService weekTableService) {
		this.weekTableService = weekTableService;
	}

	@ModelAttribute(name = "weekTable")
	public WeekTable weekTable() {
		return new WeekTable();
	}

	@ModelAttribute(name = "task")
	public Task task() {
		return new Task();
	}

	@GetMapping("")
	public String listAll(Model model) {
		model.addAttribute("weekTables", weekTableService.findAll());
		return "week-list";
	}

	@GetMapping("/find/{id}")
	public String findById(@PathVariable Long id, Model model) {
		model.addAttribute("weekTable", weekTableService.findById(id));
		return "last-table";
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert() {
		return ResponseEntity.ok(weekTableService.insert());
	}

	@GetMapping("/create")
	public String listWeekTables(Model model) throws JsonProcessingException {
		if (weekTableService.findLast() == null) {
			model.addAttribute("weekTable", new WeekTable());
			model.addAttribute("tableIsNull", true);
		} else {
			model.addAttribute("weekTable", weekTableService.findLast());
			model.addAttribute("tableIsNull", false); }

		if (basedId == null) {
			return "week-table-task";
		}

		var map = weekTableService.weekTableToMap(basedId);

		ObjectMapper objectMapper = new ObjectMapper();
		var jsonMap = objectMapper.writeValueAsString(map);

		model.addAttribute("map", map);
		model.addAttribute("basedId", basedId);
		basedId = null;
		return "week-table-task";
	}

	@GetMapping("/current")
	public String displayCurrentTable(Model model) {
		if(weekTableService.findLast() == null)
			return "no-week";
		WeekTable weekTable = weekTableService.findLast();
		model.addAttribute("weekTable", weekTableService.findLast());
		return "last-table";
	}

	@PostMapping("/save-tasks")
	public ResponseEntity<?> saveTasks(@RequestBody Map<String, List<String>> tasksByDay) {

		WeekTable weekTable = weekTableService.insert();

		for (Map.Entry<String, List<String>> entry : tasksByDay.entrySet()) {
			String day = entry.getKey();
			List<String> tasks = entry.getValue();

			if (tasks != null) {

				WeekDay weekDay = new WeekDay();
				DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
				weekDay.setDayOfWeek(dayOfWeek);
				weekDay.setWeekTable(weekTable);

				WeekDay weekDayInserted = weekDayService.insert(weekDay);

				for (String task : tasks) {
					System.out.println("Day: " + day + ", Task: " + task);
					DayTask dayTask = new DayTask();
					dayTask.setName(task);
					dayTask.setStatus(DayTask.Status.TO_DO);
					dayTask.setWeekDay(weekDayInserted);

					dayTaskService.insert(dayTask);
				}

			}
		}
		return new ResponseEntity<>("Done Saving", HttpStatus.OK);

	}

	@PostMapping("/generate-like/{id}")
	public ResponseEntity<?> generateLike(@PathVariable long id) {
		
		if(!weekTableService.findLast().isSubmitted())
			return new ResponseEntity<>("The last Table is not done yet", HttpStatus.EXPECTATION_FAILED);

		return new ResponseEntity<>(saveTasks(weekTableService.weekTableToMap(id)), HttpStatus.OK);
	}

	Long basedId;

	@PostMapping("/generate-based/{id}")
	public ResponseEntity<?> generateBased(@PathVariable long id) {

		this.basedId = id;

		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PostMapping("/submit/{id}")
	public ResponseEntity<?> submitWeek(@PathVariable Long id) {
		return weekTableService.submitWeek(id);
	}

}
