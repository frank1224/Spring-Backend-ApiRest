package com.springboot.app.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.backend.apirest.models.entity.User;

public interface IUserdao extends CrudRepository<User, Long>{

}
