package com.adeem.task.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adeem.task.config.DateConverter;
import com.adeem.task.entity.Task;
import com.adeem.task.entity.User;
import com.adeem.task.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public String listAllTasks(Model model, @AuthenticationPrincipal User user) {
        List<Task>tasks = taskService.listAll(user);
        
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
	public String insert(@ModelAttribute Task task, @AuthenticationPrincipal User user) {
		taskService.insert(task, user);
		log.info("Task saved successfully");
        return "redirect:/tasks";
	}
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String name) throws JsonMappingException, JsonProcessingException {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(name);
        if (jsonNode.has("name")) {
            String updatedName = jsonNode.get("name").asText();
            return ResponseEntity.ok(taskService.update(id, updatedName));
        }
        return ResponseEntity.badRequest().body("Invalid JSON format");
    }
    
    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submit(@PathVariable Long id){
    	return taskService.submit(id);
    }
    
    @PostMapping("/withdraw/{id}")
    public ResponseEntity<?> withdraw(@PathVariable Long id){
    	return taskService.withdraw(id);
    }
    
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id) {    	
    	return ResponseEntity.ok(taskService.deleteById(id));
    }
    
    @ModelAttribute(name = "task")
    public Task task() {
    	return new Task();
    }
    
    

}
