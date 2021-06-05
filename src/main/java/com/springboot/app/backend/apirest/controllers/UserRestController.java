package com.springboot.app.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.backend.apirest.models.entity.User;
import com.springboot.app.backend.apirest.models.services.IUserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController()
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	@Qualifier("UserService")
	private IUserService userService;

	/**
	 * 
	 * @return all users in BBDD
	 */
	@GetMapping("/users")
	public List<User> index() {

		return userService.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return specific user by id and also https status 500, 404, 200 depend on what happends.
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		User user = null;

		Map<String, Object> response = new HashMap<>();

		try {
			user = userService.findByid(id);
		} catch (DataAccessException e) {

			response.put("mensaje", " Error al realizar la consulta a la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (user == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * 
	 * @param user
	 * @return  user and  https status 201 if is successfull.
	 */
	@PostMapping("/users")
	public ResponseEntity<?> create(@RequestBody User user) {
		User newUser = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			newUser = userService.save(user);
		} catch (DataAccessException e) {
			response.put("mensaje", "error al insertar datos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con exito!!");
		response.put("user", newUser);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param user
	 * @param id
	 * @return current user changes.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping("/users/{id}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
		User currentUser = userService.findByid(id);
		User userUpdate = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (currentUser == null) {
			response.put("mensaje", "Error: no se pudo editar, El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			currentUser.setFirstName(user.getFirstName());
			currentUser.setLastName(user.getLastName());
			currentUser.setEmail(user.getEmail());
			currentUser.setCreateAt(user.getCreateAt());
			
			
			userUpdate = userService.save(currentUser);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "error al actualizar el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido actualizado con exito!!");
		response.put("user", userUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/**
	 *  Delete user by id.
	 * @param id
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			userService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "error al eliminar el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		response.put("mensaje", "El cliente ha sido eliminado con exito!!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
