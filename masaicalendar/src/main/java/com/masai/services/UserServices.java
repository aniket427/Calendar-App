package com.masai.services;

import java.util.List;

import com.masai.exceptions.EventException;
import com.masai.exceptions.UserException;
import com.masai.model.User;

public interface UserServices{

	public User register(User user) throws UserException;

	public String Login(User user) throws UserException;
	
	public List<com.masai.model.Event> allEvents(String type)throws UserException,EventException;
	
	public User updateUser(User user)throws UserException;

}
