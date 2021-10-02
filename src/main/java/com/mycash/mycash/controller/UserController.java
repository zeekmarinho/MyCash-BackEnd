package com.mycash.mycash.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycash.mycash.model.User;
import com.mycash.mycash.repository.UserRepository;

@CrossOrigin()
@RestController
@RequestMapping({"/user"})
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
//	List All - http://localhost:8095/user/login
	@RequestMapping("/login")
	@GetMapping
	public String login(HttpServletRequest request) {
		String token = request.getHeader("Authorization")
				.substring("Basic".length()).trim();
		return token;
	}
	
//	@RequestMapping("/login")
//	@GetMapping
//	public List<String> login(HttpServletRequest request) throws UnsupportedEncodingException{
//		String authorization = request.getHeader("Authorization").substring("Basic".length()).trim();
//		byte[] baseCred = Base64.getDecoder().decode(authorization);
//		String credentialsParsed = new String(baseCred, StandardCharsets.UTF_8);
//		String[] values = credentialsParsed.split(":",2);
//		User user = repository.findByUsername(values[0]);
//		
//		String token = request.getHeader("Authorization").substring("Basic".length()).trim();
//		return Arrays.asList(Boolean.toString(user.isAdmin()), token);
//	}
	
//	List All - http://localhost:8095/user
	@GetMapping
	public List<?> findAll() {
		return repository.findAll();
	}
	
// 	Find By Id - http://localhost:8095/user/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
//	Create - http://localhost:8095/user
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public User create(@RequestBody User user) {
		user.setPassword(encryptPassword(user.getPassword()));
		return repository.save(user);
	}	
	
//	Delete - http://localhost:8095/user/{id}
	@DeleteMapping(path = {"/{id}"})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id){
		return repository.findById(id)
				.map(record -> {
					repository.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
	}
	
// 	Criptografar Senha
	private String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordCrypt = passwordEncoder.encode(password);
		
		return passwordCrypt;
	}

}
