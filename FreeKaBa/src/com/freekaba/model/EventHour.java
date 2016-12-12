package com.freekaba.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="event_hour")
public class EventHour {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int event_hour_id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
	private Event event;
	
	@Column
	private int start_hour;
	
	@Column
	private int end_hour;
	
	
	public int getEvent_hour_id() {
		return event_hour_id;
	}
	public void setEvent_hour_id(int event_hour_id) {
		this.event_hour_id = event_hour_id;
	}
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public int getStart_hour() {
		return start_hour;
	}
	public void setStart_hour(int start_hour) {
		this.start_hour = start_hour;
	}
	public int getEnd_hour() {
		return end_hour;
	}
	public void setEnd_hour(int end_hour) {
		this.end_hour = end_hour;
	}

	
	
}
