package com.springboot.app.backend.apirest.models.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.backend.apirest.models.dao.IUserdao;
import com.springboot.app.backend.apirest.models.entity.User;

@Service("UserService")
public class UserServiceImp implements IUserService {

	@Autowired
	private IUserdao userDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}

}
