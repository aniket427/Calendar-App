package com.masai.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.EventException;
import com.masai.exceptions.UserException;
import com.masai.model.Event;
import com.masai.services.EventServiceImpl;

@RestController
@RequestMapping("/masaicalender")
public class EventController {
	
	@Autowired
	private EventServiceImpl mi;
	
	
	@PostMapping("/event")
	public ResponseEntity<List<Event>> createEvent(@RequestBody Event event) throws EventException, UserException{
		
		List<Event> m=mi.addEvent(event);
		return new ResponseEntity<>(m,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/event/{id}")
	public ResponseEntity<Event> updateEvent(@RequestBody Event event,@PathVariable Integer id) throws EventException, UserException{
		Event m=mi.updateEvent(id, event);
		return new ResponseEntity<>(m,HttpStatus.OK);
	}
	
	@DeleteMapping("/event/{id}")
	public ResponseEntity<String> updateEvent(@PathVariable Integer id) throws EventException, UserException{
		
		String m=mi.deleteEvent(id);
		return new ResponseEntity<>(m,HttpStatus.OK);
	}
	
	
	
	
}
