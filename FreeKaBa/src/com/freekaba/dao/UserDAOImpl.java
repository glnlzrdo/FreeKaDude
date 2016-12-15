package com.freekaba.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freekaba.model.User;

@Component
@Transactional
public class UserDAOImpl implements UserDAO{
	
	@Autowired
    private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public int createUser(User user, String username, String email) {
		List<User> users = new ArrayList<User>();
		String sql = "FROM User WHERE UPPER(username)=? OR UPPER(email)=?";
		users = (List<User>) sessionFactory.getCurrentSession()
				.createQuery(sql)
				.setParameter(0, username.toUpperCase())
				.setParameter(1, email.toUpperCase())
				.getResultList();
		if(users.size() <= 0) {
			int id = (int)sessionFactory.getCurrentSession()
					.save(user);
			System.out.println("ok");
			return id;
		} else {
			System.out.println("Already exists");
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUser(String username, String password) {
		List<User> users = new ArrayList<User>();
		String hql = "FROM User WHERE UPPER(username)=? AND password=?";
		users = (List<User>) sessionFactory.getCurrentSession()
				.createQuery(hql)
				.setParameter(0, username.toUpperCase())
				.setParameter(1, password)
				.getResultList();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFriends(int userid, int groupid) { //TODO incorporate password
		List<User> users = new ArrayList<User>();
		String hql = "FROM User WHERE group_id=? AND user_id IS NOT ?";
		users = (List<User>) sessionFactory.getCurrentSession()
				.createQuery(hql)
				.setParameter(0, groupid)
				.setParameter(1, userid)
				.getResultList();
		return users;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getCheckedFriends(List<Integer> friendIds) { //TODO incorporate password
		List<User> users = new ArrayList<User>();
		List<User> tempuser = users;
		//String hql = "FROM User WHERE user_id=?";
		for (Integer friend : friendIds) {
			tempuser = (List<User>) sessionFactory.getCurrentSession()
					.createQuery("FROM User WHERE user_id=:id")
					.setParameter("id", friend)
					.getResultList();
					users.add(tempuser.get(0));
		}
		return users;
	}
	
}
