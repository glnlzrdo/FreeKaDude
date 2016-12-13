package com.freekaba.dao;

import java.util.List;

import com.freekaba.model.User;

public interface UserDAO {
	
	public int createUser(User user);
	public User getUser(String username, String password);
	public List<User> getFriends(int userid, int groupid);
}
