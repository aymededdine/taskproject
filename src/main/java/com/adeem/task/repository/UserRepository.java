package com.adeem.task.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.User;


public interface UserRepository extends JpaRepository<User, Long>  {
	
	 User findByUsername(String username);
	 
	 



}
