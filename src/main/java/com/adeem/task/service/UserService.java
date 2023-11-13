package com.adeem.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.User;
import com.adeem.task.entity.User.Provider;
import com.adeem.task.repository.UserRepository;

@Service
public class UserService {
 
    @Autowired
    private UserRepository repo;
     
    public void processOAuthPostLogin(String email) {
        User existUser = repo.findByUsername(email);
         
        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(email.split("@")[0]);
            newUser.setProvider(Provider.FACEBOOK);             
            repo.save(newUser);        
        }
         
    }}
     
