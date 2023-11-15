package com.adeem.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.adeem.task.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@GetMapping("/is-auth")
	public ResponseEntity<?> taskForm(@AuthenticationPrincipal User user) {
		var isAuth = false;
		
		if(user != null) {
			isAuth = true;
			return new ResponseEntity<>("isAuth", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("isAuth", HttpStatus.EXPECTATION_FAILED);

	}
	
	@GetMapping("/qwerty")
	public ResponseEntity<?> isAmouna(@AuthenticationPrincipal User user){
	    if (user != null && user.getUsername().equals("boubidiimen")) {
	        return new ResponseEntity<>("isAmouna", HttpStatus.UNAUTHORIZED);
	    }
	    return new ResponseEntity<>("Not Authorized", HttpStatus.OK);
	}


}
