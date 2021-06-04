package com.springboot.app.backend.apirest.models.service;

import java.util.List;

import com.springboot.app.backend.apirest.models.entity.User;

public interface IUserService {
	
	public List<User> findAll();
}
