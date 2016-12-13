package com.freekaba.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freekaba.model.Event;
import com.freekaba.model.User;
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
		sessionFactory.getCurrentSession()
				.update(event);
		return event;
	}

	@Override
	public void deleteEvent(int event_id) {
		Event event = (Event)getEvent(event_id);
		sessionFactory.getCurrentSession()
				.delete(event);
	}

	@Override
	public List<Event> getAllEvents(int user_id) {
		
		return null;
	}

	@Override
	public Event getEvent(int event_id) {
		Event eventResult = (Event) sessionFactory.getCurrentSession()
				.createQuery("FROM event WHERE event_id=?");
		return eventResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getEventsByDate(int user_id, Date from, Date to) { //TODO
		
		List<Event> listResult = new ArrayList<Event>();
			listResult = (List<Event>) sessionFactory.getCurrentSession()
				.createQuery("FROM Event WHERE user_id = :id AND (start BETWEEN :from AND :to)")
				.setParameter("id", user_id)
				.setParameter("from", from)
				.setParameter("to", to)
				.getResultList();
				
		return listResult;
	}

}
