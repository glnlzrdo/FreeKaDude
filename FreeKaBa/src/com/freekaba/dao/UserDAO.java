package com.freekaba.dao;

import com.freekaba.model.User;

public interface UserDAO {
	
	public int createUser(User user);
	public User getUser(String username);
//	public List<User> getAllUsersInGroup(int group_id);
	
}