package com.freekaba.dummydata;

import com.freekaba.model.User;

public class Dummy {

	public static User createDummyUser1(){
		User user = new User(1, "eman", "1234", "eman@freekaba.com");
		return user;
	}
	
	public static User createDummyUser2(){
		User user = new User(7, "charles", "1234", "charles@freekaba.com");
		return user;
	}
	
	public static User createDummyUser3(){
		User user = new User(8, "glenn", "1234", "glenn@freekaba.com");
		return user;
	}
	
}
