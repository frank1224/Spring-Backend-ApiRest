package com.springboot.app.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.backend.apirest.models.entity.User;
import com.springboot.app.backend.apirest.models.services.IUserService;

@RestController()
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	@Qualifier("UserService")
	private IUserService userService;
	
	@GetMapping("/users")
	public List<User> index(){
		
		return userService.findAll();
	}
	

}
