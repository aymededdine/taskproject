package com.adeem.task.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adeem.task.config.DateConverter;
import com.adeem.task.entity.Task;
import com.adeem.task.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/tasks")
public class TaskController {
	


    @Autowired
    private TaskService taskService;
    
    @Autowired
    private DateConverter dateConverter;

    @GetMapping
    public String listAllTasks(Model model) {
        List<Task> tasks = taskService.listAll();
        
        model.addAttribute("tasks", tasks);
        return "task-list"; 
    }

    @GetMapping("/task/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        Optional<Task> task = taskService.findById(id);
        task.ifPresent(value -> model.addAttribute("task", value));
        return "task-details"; 
    }
    
    @GetMapping("/task-form")
    public String taskForm() {
    
    	return "task-form";
    }
    
    
    @PostMapping("/add")
	public String insert(@ModelAttribute Task task) {
//		if (errors.hasErrors()) {
//	        return "redirect:/tasks";
//			}
		taskService.insert(task);
		log.info("Task saved successfully");
        return "redirect:/tasks";
	}
    
    
    @PostMapping("/update")
    public String update(Task task) {
    	taskService.update(task);
		log.info("Task updated successfully");
        return "redirect:/tasks";
    }
    
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id) {
    	//Long id = task.getId();
    	
    	return ResponseEntity.ok(taskService.deleteById(id));
    }
    
    @ModelAttribute(name = "task")
    public Task task() {
    	return new Task();
    }
    
    

}
