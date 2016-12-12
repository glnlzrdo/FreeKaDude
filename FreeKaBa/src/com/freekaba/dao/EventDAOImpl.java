package com.freekaba.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freekaba.model.Event;
import com.freekaba.util.UserUtil;

@Component
@Transactional
public class EventDAOImpl implements EventDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public int createEvent(Event event) {
		int id = (int)sessionFactory.getCurrentSession()
				.save(event);
		return id;
	}

	@Override
	public Event updateEvent(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEvent(int event_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Event> getAllEvents(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event getEvent(int event_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getAllEvents(int user_id, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

}
