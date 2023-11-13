package com.adeem.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.adeem.task.entity.CustomOAuth2User;
import com.adeem.task.entity.User;
import com.adeem.task.repository.UserRepository;
import com.adeem.task.service.CustomOAuth2UserService;
import com.adeem.task.service.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomOAuth2UserService oauthUserService;
	
	@Autowired
	UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			if (user != null)
				return user;
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		 return http
//				 .authorizeRequests()
//				 .antMatchers("/weekly-tasks/**", "/tasks", "/statistics").access("hasRole('USER')")
//				 .antMatchers("/", "/**").access("permitAll()")
//				 .and()
//				 .formLogin()
//				 .loginPage("/login")
//				 .and()
//				 .oauth2Login()
//				 .loginPage("/login")
//				 .and()
//				 .logout()
//				 .and()
//				 .build();
//	}
	
	
	@Bean
	public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
	    return (request, response, authentication) -> {
	        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
	        userService.processOAuthPostLogin(oauthUser.getEmail());
	        response.sendRedirect("/");
	    };
	}
	
	  @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	            .antMatchers("/weekly-tasks/**", "/tasks", "/statistics").hasRole("USER")
	            .antMatchers("/", "/**").permitAll()
	            .anyRequest().authenticated()
	            .and()
	            .formLogin().loginPage("/login")
	            .and().logout()
	            .and()
	            .oauth2Login()
	                .loginPage("/login")
	                .userInfoEndpoint()
	                    .userService(oauthUserService)
	                    .and()
	                    .successHandler(oauth2AuthenticationSuccessHandler());
	    }
	

}
