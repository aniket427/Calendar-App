package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.EventException;
import com.masai.exceptions.UserException;
import com.masai.model.Event;
import com.masai.model.User;
import com.masai.services.UserServices;

@RestController
@RequestMapping("/masaicalendar")
public class UserController {

	@Autowired
	UserServices userService;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) throws UserException {
		User userReturn = userService.register(user);
		return new ResponseEntity<User>(userReturn, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) throws UserException {
		String userReturn = userService.Login(user);
		return new ResponseEntity<>(userReturn, HttpStatus.OK);
	}

	@GetMapping("event/{type}")
	public ResponseEntity<List<Event>> getEventDetails(@PathVariable String type) throws EventException, UserException {
		List<Event> u1 = userService.allEvents(type);
		return new ResponseEntity<>(u1, HttpStatus.OK);
	}

	@PutMapping("/user")
	public ResponseEntity<User> updateUser(@RequestBody User user) throws UserException {
		User u1 = userService.updateUser(user);
		return new ResponseEntity<>(u1, HttpStatus.OK);
	}
}
