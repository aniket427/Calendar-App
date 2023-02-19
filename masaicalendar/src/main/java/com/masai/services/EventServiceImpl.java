package com.masai.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.EventException;
import com.masai.exceptions.UserException;
import com.masai.model.Auth;
import com.masai.model.Event;
import com.masai.model.User;
import com.masai.repository.AuthRepo;
import com.masai.repository.EventRepo;
import com.masai.repository.UserRepo;

@Service
public class EventServiceImpl implements EventServices {

	@Autowired
	private UserRepo uRepo;

	@Autowired
	private AuthRepo aRepo;

	@Autowired
	private EventRepo eRepo;

	@Override
	public List<Event> addEvent(Event event) throws EventException, UserException {
		
		List<Auth> lin = aRepo.findAll();
		if (lin.size() > 0) {
			for (Auth l : lin) {

				Optional<User> u1 = uRepo.findById(l.getUserEmail());

				LocalDate start = event.getEventStartDate();
				LocalDate end = event.getEventEndDate();

				List<LocalDate> totalDates = new ArrayList<>();
				while (!start.isAfter(end)) {
					totalDates.add(start);
					start = start.plusDays(1);
				}

				List<Event> ev1 = new ArrayList<>();
				List<Event> ev2 = new ArrayList<>();
				for (Event e : u1.get().getEvents()) {
					ev1.add(e);
				}

				for (LocalDate e : totalDates) {
					Event ev = new Event();
					ev.setDescription(event.getDescription());
					ev.setEventEndDate(e);
					ev.setEventStartDate(e);
					ev.setEndTime(event.getEndTime());
					ev.setStartTime(event.getStartTime());
					ev.setUser(u1.get());

					ev1.add(ev);

				}

				u1.get().setEvents(ev1);

				return ev2;

			}
			throw new EventException("error");
		} else {
			throw new UserException("User not authorised, need to login first");
		}
	}

	@Override
	public Event updateEvent(Integer eventId, Event event) throws EventException, UserException {

		List<Auth> lin = aRepo.findAll();
		if (lin.size() > 0) {
			for (Auth l : lin) {

				Optional<User> u1 = uRepo.findById(l.getUserEmail());

				List<Event> eventList = u1.get().getEvents();
				if (eventList.size() > 0) {

					for (Event m : eventList) {

						if (m.getEventId() == eventId) {
							m.setDescription(event.getDescription());
							m.setEndTime(event.getEndTime());
							m.setStartTime(event.getStartTime());
							u1.get().setEvents(eventList);
							uRepo.save(u1.get());
							return m;

						}
					}

					throw new EventException("No Event found by Id :" + eventId);
				}
			}
			throw new EventException("No events found");
		} else {
			throw new UserException("You need to login First");
		}

	}

	@Override
	public String deleteEvent(Integer eventId) throws EventException, UserException {

		List<Auth> lin = aRepo.findAll();
		if (lin.size() > 0) {
			for (Auth l : lin) {

				Optional<User> u1 = uRepo.findById(l.getUserEmail());

				List<Event> eventList = u1.get().getEvents();
				if (eventList.size() > 0) {

					for (Event m : eventList) {

						if (m.getEventId() == eventId) {
							Optional<Event> e3 = eRepo.findById(eventId);
							eRepo.deleteById(eventId);
							eventList.remove(e3);
							u1.get().setEvents(eventList);
							uRepo.save(u1.get());
							return "mail Deleted successfully";
						}
					}

					throw new EventException("No Events found by Id : " + eventId);
				}
			}
			throw new EventException("No events present");
		} else {
			throw new UserException("You need to login First");
		}

	}

}
