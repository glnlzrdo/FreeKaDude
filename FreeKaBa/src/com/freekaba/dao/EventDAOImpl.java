package com.freekaba.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Where;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
		sessionFactory.getCurrentSession().update(event);
		return event;
	}

	@Override
	public void deleteEvent(int event_id) {
		Event myevent = (Event) getEvent(event_id);
		sessionFactory.getCurrentSession().delete(myevent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAllEvents(int user_id) {
		List<Event> events = new ArrayList<>();
				events = (List<Event>) sessionFactory.getCurrentSession().createQuery("FROM Event WHERE user_id = :id")
						.setParameter("id", user_id).getResultList();
		return events;
	}

	@Override
	public Event getEvent(int event_id) {
		Event eventResult = (Event) sessionFactory.getCurrentSession().createQuery("FROM Event WHERE event_id= :id")
					.setParameter("id", event_id).getSingleResult();
		return eventResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Event getLastEvent(int user_id) {
		//Session session = sessionFactory.getCurrentSession();
		DetachedCriteria query = DetachedCriteria.forClass(Event.class);
		query.add(Restrictions.eq("user_id", user_id));
		query.addOrder(Order.desc("event_id"));
		
		List<Event> results = query.getExecutableCriteria(sessionFactory.getCurrentSession()).setMaxResults(1).list();		
	return results.get(0);
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