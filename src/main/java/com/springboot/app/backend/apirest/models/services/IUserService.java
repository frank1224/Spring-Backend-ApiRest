package com.springboot.app.backend.apirest.models.services;

import java.util.List;

import com.springboot.app.backend.apirest.models.entity.User;

public interface IUserService {
	
	public List<User> findAll();
	
	public User findByid(Long id);
	
	public User save(User user);
	
	public void delete(Long id);
}
