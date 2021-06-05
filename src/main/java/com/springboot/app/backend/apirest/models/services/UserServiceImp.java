package com.springboot.app.backend.apirest.models.services;

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

	@Transactional(readOnly = true)
	@Override
	public User findByid(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public User save(User user) {
		return userDao.save(user);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		userDao.deleteById(id);
	}

}
