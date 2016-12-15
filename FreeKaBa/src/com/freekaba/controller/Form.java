package com.freekaba.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freekaba.dao.EventDAO;
import com.freekaba.dao.UserDAO;
import com.freekaba.dummydata.Dummy;
import com.freekaba.model.CalendarData;
import com.freekaba.model.Event;
import com.freekaba.model.User;

@SessionAttributes("user")
@Controller
@RequestMapping("App")
public class Form {
	@Autowired
	private UserDAO userDao;
	@Autowired
	private EventDAO eventDao;
	
	@RequestMapping("/login")
	public ModelAndView showForm(){
		ModelAndView model = new ModelAndView("login");
		return model;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView authenticate(User user, SessionStatus status, 
			@RequestParam(value = "error", required = false) String error){
		User userResult = userDao.getUser(user.getUsername(), user.getPassword());
		
		
		ModelAndView model = new ModelAndView();
		if(userResult != null) {
			List<User> friends = userDao.getFriends(userResult.getUser_id(), userResult.getGroup_id());
			model.addObject("user", userResult);
			model.addObject("friends", friends);
			model.addObject("login", "new");
			model.setViewName("main");
			return model;
		} else {
			model.addObject("error", "Invalid username or password!");
			model.setViewName("login");
			return model;
		}
	}
	
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ModelAndView registerUser(User user, String username, String email) {
		user.setGroup_id(1); //placeholder
		ModelAndView model = new ModelAndView();
		int checker = userDao.createUser(user, user.getUsername(), user.getEmail());
		if(checker==0) {
			model.addObject("regfail", "User already exists.");
		} else {
			model.addObject("regok", "New account created.");
		}
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest request, 
			@RequestParam(value = "logout", required = false) String logout) {
		request.getSession().invalidate();
		ModelAndView model = new ModelAndView();
		
		HttpSession session = request.getSession(false);
		if(session==null || !request.isRequestedSessionIdValid()) {
			model.addObject("msg", "You have successfully logged out.");
		}
		model.setViewName("login");
		return model;
	}
	
	//turBORAT
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteEvent(Event event, User user) throws JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(event);
		eventDao.deleteEvent(event.getEvent_id());
		return jsonInString;
	}
		
	//turBORAT
	@ResponseBody
	@RequestMapping(value ="/loadEvents", method = RequestMethod.POST)
	public String loadEventsFromDbToCal(User user) throws JsonProcessingException {
			
		ObjectMapper mapper = new ObjectMapper();
		List<Event> events = eventDao.getAllEvents(user.getUser_id());		
		List<CalendarData> calData = new ArrayList<CalendarData>();		
		for (Event event : events) {
			calData.add(new CalendarData(event.getEvent_id(), event.getDescription(), event.getStart().toString(), event.getEnd().toString()));
		}
		
<<<<<<< HEAD
		String  jsonInString = mapper.writeValueAsString(calData);		
=======
		String  jsonInString = mapper.writeValueAsString(calData);	
>>>>>>> Charles
		return jsonInString;
	}
	
	//turBORAT
<<<<<<< HEAD
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateEvent(Event event, User user) throws JsonProcessingException, ParseException {			
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(event);
		Event dbEvent = eventDao.getEvent(event.getEvent_id());
		System.out.println("=======================");
		System.out.println(event.getDescription() + "\n" + event.getStart() + "\n" + event.getEnd());
		dbEvent.setDescription(event.getDescription());
		//dbEvent.setStart(event.getStart());
		//dbEvent.setEnd(event.getEnd());
		eventDao.updateEvent(dbEvent);
		return jsonInString;

	}
		
	//EMAN //turBORAT
	@ResponseBody
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String getSearchResultViaAjax(Event event, User user) throws JsonProcessingException, ParseException {			
		
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		event.setStart(sdf.parse(event.getStartTime()));
		event.setEnd(sdf.parse(event.getEndTime()));
		System.out.println(sdf.parse(event.getStartTime()));
		
		event.setUser_id(user.getUser_id());
		
		eventDao.createEvent(event);
		
		ObjectMapper mapper = new ObjectMapper();
		Event lastEvent = eventDao.getLastEvent(user.getUser_id());
		//Object to JSON in String
		String jsonInString = mapper.writeValueAsString(lastEvent.getEvent_id());
=======
		@ResponseBody
		@RequestMapping(value = "/update", method = RequestMethod.POST)
		public String updateEvent(Event event, User user) throws JsonProcessingException, ParseException {			
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(event);
			Event dbEvent = eventDao.getEvent(event.getEvent_id());
			System.out.println("=======================");
			System.out.println(event.getDescription() + "\n" + event.getStart() + "\n" + event.getEnd());
			dbEvent.setDescription(event.getDescription());
			//dbEvent.setStart(event.getStart());
			//dbEvent.setEnd(event.getEnd());
			eventDao.updateEvent(dbEvent);
			return jsonInString;
		}
>>>>>>> Charles
		
	//EMAN //turBORAT
		@ResponseBody
		@RequestMapping(value = "/process", method = RequestMethod.POST)
		public String getSearchResultViaAjax(Event event, User user) throws JsonProcessingException, ParseException {			
			
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			
			event.setStart(sdf.parse(event.getStartTime()));
			event.setEnd(sdf.parse(event.getEndTime()));
			System.out.println(sdf.parse(event.getStartTime()));
			
			event.setUser_id(user.getUser_id());
			
			eventDao.createEvent(event);
			
			ObjectMapper mapper = new ObjectMapper();
			Event lastEvent = eventDao.getLastEvent(user.getUser_id());
			//Object to JSON in String
			String jsonInString = mapper.writeValueAsString(lastEvent.getEvent_id());
			
			return jsonInString;

<<<<<<< HEAD
	}
	
=======
		}
		
	@RequestMapping(value="GetAllData", method = RequestMethod.POST)
	public String getAllDate(User user){
		//TODO still needed?	
		return null;
	}
>>>>>>> Charles
		
	//EMAN
	@ResponseBody
	@RequestMapping(value = "/Search", method = RequestMethod.POST)
	public ModelAndView searchFreeTime(@RequestParam("searchFrom") String from,
			@RequestParam("searchTo") String to, @RequestParam("checkedfriends") List<String> friendIds, User user) throws ParseException {
		String toDate = to.substring(0, 10);
		String fromDate = from.substring(0, 10);
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Integer> userIds = new ArrayList<>(); //Get all user_ids to search
		userIds.add(user.getUser_id());	//dummy
		for(String userId : friendIds){
			userIds.add(Integer.parseInt(userId));
		}
		
		
		
		Date searchFrom = sdf.parse(from);  
		Date searchTo = sdf.parse(to);
		
		LocalDate ldFrom = LocalDate.parse(fromDate);
	
		LocalDate ldTo = LocalDate.parse(toDate);
		//Determine length of search in days
		int lengthOfSearch = Period.between(ldFrom, ldTo).getDays();
			
		HashMap<String, ArrayList<Integer>> eventHoursMap = new HashMap<>(); //Each list will contain a user's collection of events
		//Retrieve all events for each user, between the searchdates
		for(Integer user2 : userIds){
			List<Event> listEvent = new ArrayList<>();
			listEvent = eventDao.getEventsByDate(user.getUser_id(), searchFrom, sdf.parse(ldTo.plusDays(1).toString()));
			//listListEvent.add((ArrayList<Event>) listEvent);
			if(listEvent != null){
				ArrayList<Integer> hourList = new ArrayList<>();
				for(Event event : listEvent){
					LocalDate eventStart = LocalDate.parse(event.getStart().toString().substring(0, 10));
					LocalDate eventEnd = LocalDate.parse(event.getEnd().toString().substring(0, 10)).plusDays(1);
					int multiplier = Period.between(ldFrom, eventStart).getDays();
					int startHour = Integer.parseInt(event.getStart().toString().substring(11, 13)) + (multiplier*24);
					int endHour = 0;
					if(eventEnd.isBefore(ldTo)){
						endHour = Integer.parseInt(event.getEnd().toString().substring(11, 13)) + (multiplier*24);
					}else{
						endHour = 24*lengthOfSearch;
					}
				/*	System.out.println(eventStart);
					System.out.println("start hour = " + startHour);
					System.out.println("end hour = " + endHour);*/
					for(int i = startHour; i <= endHour ; i++){
						if(!hourList.contains(i)){
							hourList.add(i);
						}
					}	
				}
				System.out.println(user.getEmail() + ": " +hourList);
				eventHoursMap.put(user.getEmail(), hourList);
			}
		}	
		//Set the hours to check
		ArrayList<Integer> searchHours = new ArrayList<>();
		for(int i = 0; i <= lengthOfSearch*24; i++){
			searchHours.add(i);
		}
		System.out.println(searchHours.toString());	
		
		for(ArrayList<Integer> list : eventHoursMap.values()){
			searchHours.removeAll(list);
		}
		System.out.println("Common Free times" + searchHours);
		
		List<User> friendstop = userDao.getFriends(user.getUser_id(), user.getGroup_id());
		List<User> friendsbottom = userDao.getCheckedFriends(userIds);
		
		if(searchHours.size() != 0){
			
			Map <Integer, Integer>freeTimeRanges = new TreeMap();
			
			ArrayList<Integer> timeList = new ArrayList<>();
			timeList.add(searchHours.get(0));
			for(int i = 0; i< searchHours.size(); i++){
				if(i != searchHours.size()-1 && searchHours.get(i+1) - searchHours.get(i) == 1){
					timeList.add(searchHours.get(i+1));
				}else{
					freeTimeRanges.put(timeList.get(0), timeList.get(timeList.size()-1));
					timeList.clear();
					if(i != searchHours.size()-1)
						timeList.add(searchHours.get(i+1));
				}
			}
			System.out.println(freeTimeRanges.toString());
				
			ArrayList<ArrayList> dateListList = new ArrayList();
				
			int j = 1;
			for (Map.Entry<Integer, Integer> entry : freeTimeRanges.entrySet()) {
				ArrayList dateList = new ArrayList();
				
				int key = entry.getKey();
			    int value = entry.getValue();
			    
			    int dayPastStart = key/24;
			    int hourStart = key%24;
			    int dayPastEnd = value/24;
			    int hourEnd = value%24;
			    LocalDateTime freeTimeStartLDT = ldFrom.atTime(0, 0).plusDays(dayPastStart).plusHours(hourStart);
			    LocalDateTime freeTimeEndLDT = ldFrom.atTime(0, 0).plusDays(dayPastEnd).plusHours(hourEnd);
			    
			    LocalDateTime tempDateTime = LocalDateTime.from( freeTimeStartLDT );
			    long days = tempDateTime.until( freeTimeEndLDT, ChronoUnit.DAYS);
			    tempDateTime = tempDateTime.plusDays( days );

			    long hours = tempDateTime.until( freeTimeEndLDT, ChronoUnit.HOURS);
			    tempDateTime = tempDateTime.plusHours( hours );
			    
			    String freeTimeLength =  days + " day/s " + hours + " hours ";
			    
			    dateList.add(DateTimeFormatter.ofPattern("uuuu MMM d, h a").format(freeTimeStartLDT));
			    dateList.add(DateTimeFormatter.ofPattern("uuuu MMM d, h a").format(freeTimeEndLDT));
			    dateList.add(freeTimeLength);
			    dateListList.add(dateList);
			}
			ModelAndView model = new ModelAndView("main");
				
			model.addObject("friends", friendstop);
			model.addObject("friendsbottom", friendsbottom);
			model.addObject("dateListList", dateListList);
			System.out.println(dateListList);
			return model;
		} else {
			ModelAndView model = new ModelAndView("main");
			model.addObject("friends", friendstop);
			return model;
		}
		
	}
}
