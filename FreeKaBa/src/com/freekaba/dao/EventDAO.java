package com.freekaba.dao;

import java.util.Date;
import java.util.List;

import com.freekaba.model.Event;

public interface EventDAO {
	public int createEvent(Event event);
	public Event updateEvent(Event event);
	public void deleteEvent(int event_id);
	public List<Event> getAllEvents(int user_id);
    public Event getEvent(int event_id);   
    public List<Event> getEventsByDate(int user_id, Date from, Date to);
}
