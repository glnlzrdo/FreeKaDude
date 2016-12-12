package com.freekaba.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.freekaba.model.Activity;
import com.freekaba.model.Event;
import com.freekaba.model.EventHour;
import com.freekaba.model.User;
import javassist.bytecode.Descriptor.Iterator;

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
	
	@RequestMapping("/main")
	public ModelAndView main(){
		ModelAndView model = new ModelAndView("main");
		
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String getSearchResultViaAjax(Activity activity) throws JsonProcessingException {

		
		
		ObjectMapper mapper = new ObjectMapper();
//		Staff obj = new Staff();

		//Object to JSON in String
		String jsonInString = mapper.writeValueAsString(activity);
		
		
		System.out.println(activity.getTitle());
		System.out.println(activity.getStartDate());
		System.out.println(activity.getEndDate());
		
		System.out.println(jsonInString);
		return jsonInString;

	}
	
	
	
	@RequestMapping(params = "submit", method = RequestMethod.POST)
	public ModelAndView register(User user){
		ModelAndView model = new ModelAndView("home");
		
		int result = userDao.createUser(user);
		model.addObject("result", result );
		return model;
	}
	
	@RequestMapping(params = "login", method = RequestMethod.POST)
	public ModelAndView authenticate(User user){
		ModelAndView model = new ModelAndView("home");
	
		User userResult = userDao.getUser(user.getUsername());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(userResult);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(jsonInString.toString());
		
		
		model.addObject("user", userResult );
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
	
//	@RequestMapping(params = "addEvent", method = RequestMethod.POST)
	public ModelAndView addEvent(@RequestParam ("hour") List<String> hour,
			@RequestParam ("event") List<String> event,
			/*@RequestParam ("allday") int allday, */User user){
		//eventDao.createEvent(new Event(user, description, date));
		System.out.println("hour = " + hour);
		System.out.println("event = " + event);
		
		Map<String, List> map = new HashMap<>();
		List<Integer> list = new ArrayList<>();
		
		String prevEvent = event.get(0);
		int prevEventHour = Integer.parseInt(hour.get(0));
		for(int i = 0; i < hour.size(); i++){
			String currEvent = event.get(i);
			int currHour = Integer.parseInt(hour.get(i));
			
			if(currHour - prevEventHour == 1 && prevEvent.equals(currEvent)){

				list.add(currHour);
				map.put(currEvent, list);
			
				if (i == hour.size() - 1){
					ArrayList <Integer> values = (ArrayList<Integer>) map.get(prevEvent);
					System.out.println("add " + prevEvent + ": " + (values.get(0) - 1) + " until " + values.get(values.size()-1));
				}	
			}else{

				if(list.size() != 0){
					ArrayList <Integer> values = (ArrayList<Integer>) map.get(prevEvent);
					System.out.println("add " + prevEvent + ": " + (values.get(0) - 1) + " until " + values.get(values.size()-1));
					list.clear();
					map.clear();
					if (i == hour.size() - 1){
						System.out.println("add1 curr event and hour: " + currEvent + " at " + currHour);
					}else if(!currEvent.equals(prevEvent) && !currEvent.equals(event.get(i+1))){
						System.out.println("add2 curr event and hour: " + currEvent + " at " + currHour);
					}else if(Integer.parseInt(hour.get(i+1)) - currHour != 1 || currHour - prevEventHour != 1){
						System.out.println("add3.5 curr event and hour: " + currEvent + " at " + currHour);
					}
				}else{
					if(i + 1 >= hour.size()){
						System.out.println("add4 curr event and hou r: " + currEvent + " at " + currHour);
					}else if(!currEvent.equals(prevEvent) && !currEvent.equals(event.get(i+1))){
						System.out.println("add5 curr event and hour: " + currEvent + " at " + currHour);
					}else if(Integer.parseInt(hour.get(0)) == currHour && !event.get(i+1).equals(currEvent) || Integer.parseInt(hour.get(i+1)) - currHour != 1){
						System.out.println("add7 curr event and hour: " + currEvent + " at " + currHour);
					}else if(currHour - prevEventHour != 1 && !event.get(i+1).equals(currEvent) ){
						System.out.println("add7 curr event and hour: " + currEvent + " at " + currHour);
					}
				}
					
			}
			prevEvent = currEvent;
			prevEventHour = currHour;
		
		}
//		eventDao.createEvent(new Event(user, event.get(i), new Date()));
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public String registerUser(User user) {
		userDao.createUser(user);
		
		System.out.println("Registered");
		return "redirect:login";
	}
	
}
