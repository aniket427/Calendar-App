package com.masai.services;

import java.util.List;

import com.masai.exceptions.EventException;
import com.masai.exceptions.UserException;
import com.masai.model.Event;

public interface EventServices {

	public List<Event> addEvent(Event event) throws EventException, UserException;

	public Event updateEvent(Integer EventId, Event event) throws EventException, UserException;

	public String deleteEvent(Integer EventId) throws EventException, UserException;
}
