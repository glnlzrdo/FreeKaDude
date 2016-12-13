package com.freekaba.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
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
			model.addObject("user", userResult);
			model.setViewName("main");
			return model;
		} else {
			model.addObject("error", "Invalid username and password!");
			model.setViewName("login");
			return model;
		}
	}
	
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public String registerUser(User user) {
		userDao.createUser(user);
		return "redirect:login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest request, 
			@RequestParam(value = "logout", required = false) String logout) {
		request.getSession().invalidate();
		ModelAndView model = new ModelAndView();
		
		HttpSession session = request.getSession(false);
		if(session==null || !request.isRequestedSessionIdValid()) {
			model.addObject("msg", "You've been successfully logged out.");
		}
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(params = "addEvent", method = RequestMethod.POST)
	public ModelAndView getAjax(User user){
		ModelAndView model = new ModelAndView("home");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(jsonInString.toString());
		
		return model;
	}
	
	//EMAN
		@ResponseBody
		@RequestMapping(value = "/process", method = RequestMethod.POST)
		public String getSearchResultViaAjax(Event event, User user) throws JsonProcessingException, ParseException {
			
			ObjectMapper mapper = new ObjectMapper();

			//Object to JSON in String
			String jsonInString = mapper.writeValueAsString(event);
			
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			
			event.setStart(sdf.parse(event.getStartTime()));
			event.setEnd(sdf.parse(event.getEndTime()));
			System.out.println(sdf.parse(event.getStartTime()));
			
			event.setUser_id(Dummy.createDummyUser2().getUser_id()); //DUMMY
			
			int num = eventDao.createEvent(event);
			System.out.println(num);
			
			return jsonInString;

		}
		
		@RequestMapping(value="GetAllData", method = RequestMethod.POST)
		public String getAllDate(User user){
			
			
			return null;
		}
		
		//EMAN
		@RequestMapping(value = "Search", method = RequestMethod.POST)
		public void searchFreeTime(@RequestParam("searchFrom") String from,
				@RequestParam("searchTo") String to) throws ParseException {
			
			String toDate = to.substring(0, 10);
			String fromDate = from.substring(0, 10);
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<User> userList = new ArrayList<>(); //Get all user_ids to search
			userList.add(Dummy.createDummyUser1());	//dummy
			userList.add(Dummy.createDummyUser2()); //TEST
			userList.add(Dummy.createDummyUser3());
			
			Date searchFrom = sdf.parse(from);  
			Date searchTo = sdf.parse(to);
			
			LocalDate ldFrom = LocalDate.parse(fromDate);
		
			LocalDate ldTo = LocalDate.parse(toDate);
			//Determine length of search in days
			int lengthOfSearch = Period.between(ldFrom, ldTo).getDays();
			
			HashMap<String, ArrayList<Integer>> eventHoursMap = new HashMap<>(); //Each list will contain a user's collection of events
			//Retrieve all events for each user, between the searchdates
			for(User user : userList){
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
			    Date freeTimeStartToDate =  Date.from(freeTimeStartLDT.atZone(ZoneId.systemDefault()).toInstant());
			    Date freeTimeEndToDate =  Date.from(freeTimeEndLDT.atZone(ZoneId.systemDefault()).toInstant());
			    
			    dateList.add(freeTimeStartToDate);
			    dateList.add(freeTimeEndToDate);
			    dateListList.add(dateList);
			    System.out.println("from " + key + " to " + value);
			}
			
			System.out.println("Free on:");
			for (ArrayList list : dateListList){
				System.out.println("From " + list.get(0) + " to " + list.get(1));
			}
			
	/*		LocalDateTime startExactTime = ldFrom.atTime(0, 0).plusHours(searchHours.get(0));

			System.out.println("Free from " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(startExactTime));*/
		}
	
}
