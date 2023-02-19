package com.masai.services;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.EventException;
import com.masai.exceptions.UserException;
import com.masai.model.Auth;
import com.masai.model.Event;
import com.masai.model.User;
import com.masai.repository.AuthRepo;
import com.masai.repository.UserRepo;

@Service
public class UserServiceImpl implements UserServices {
	@Autowired
	private UserRepo uRepo;

	@Autowired
	private AuthRepo aRepo;

	@Override
	public User register(User user) throws UserException {

		Optional<User> userReturn = uRepo.findById(user.getEmail());
		if (userReturn.isEmpty()) {

			User u2 = uRepo.save(user);
			return u2;

		} else {
			throw new UserException("User already exist");
		}

	}

	@Override
	public String Login(User user) throws UserException {
		List<Auth> lin = aRepo.findAll();
		if (lin.size() > 0) {
			throw new UserException("User has already logged in");
		} else {

			List<User> userList = uRepo.findAll();

			for (User u : userList) {
				if (u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword())) {
					Auth lin2 = new Auth();
					lin2.setUserEmail(user.getEmail());
					lin2.setPresent(true);
					aRepo.save(lin2);
					return "Login Successful";
				}
			}

			throw new UserException("No user found for supplied MailId and Passwprd");

		}

	}

	@Override
	public List<Event> allEvents(String ty) throws EventException, UserException {

		List<Auth> lin = aRepo.findAll();
		if (lin.size() > 0) {

			for (Auth l : lin) {

				Optional<User> u1 = uRepo.findById(l.getUserEmail());

				String type = ty.toLowerCase();

				if (type.equals("day")) {

					List<Event> EventList = u1.get().getEvents();

					boolean flag = true;
					for (Event e : u1.get().getEvents()) {
						if (e.getEventStartDate().compareTo(LocalDate.now()) == 0) {
							flag = false;
							EventList.add(e);
						}
					}
					if (flag) {
						throw new EventException("No events Found");
					} else {
						return EventList;
					}

				} else if (type.equalsIgnoreCase("week")) {

					List<Event> EventList = u1.get().getEvents();

					boolean flag = true;

					for (Event e : u1.get().getEvents()) {

						WeekFields week1 = WeekFields.of(Locale.getDefault());
						int weekNum1 = e.getEventStartDate().get(week1.weekOfWeekBasedYear());

						WeekFields week2 = WeekFields.of(Locale.getDefault());
						int weekNum2 = LocalDate.now().get(week2.weekOfWeekBasedYear());

						if (weekNum1 == weekNum2 && e.getEventStartDate().getMonth().compareTo(LocalDate.now().getMonth()) == 0) {

							flag = false;
							EventList.add(e);
						}

					}
					if (flag) {
						throw new EventException("No events Found");
					} else {
						return EventList;
					}

				} else if (type.equals("month")) {

					List<Event> EventList = u1.get().getEvents();

					boolean flag = true;

					for (Event e : u1.get().getEvents()) {
						if (e.getEventStartDate().getMonth().compareTo(LocalDate.now().getMonth()) == 0) {

							flag = false;
							EventList.add(e);
						}
					}
					if (flag) {
						throw new EventException("No events Found");
					} else {
						return EventList;
					}

				}

			}
			throw new EventException("No events Found");

		} else {
			throw new UserException("User not authorised");
		}
	}

	@Override
	public User updateUser(User user) throws UserException {

		Optional<User> u1 = uRepo.findById(user.getEmail());
		if (u1.isPresent()) {

			if (u1.get().getPassword().equals(user.getPassword())) {
				User u2 = uRepo.save(user);
				return u2;
			} else {
				throw new UserException("Invalid username or password");
			}
		} else {
			throw new UserException("User does not exist");
		}

	}

}
