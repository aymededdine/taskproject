package com.adeem.task.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.adeem.task.entity.User.Provider;

import lombok.Data;

@Data
public class RegistrationForm {

	private String username;
	private String email;
	private String password;

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), email, Provider.LOCAL);
	}

}
