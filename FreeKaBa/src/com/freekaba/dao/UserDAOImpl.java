package com.freekaba.dao;

import java.util.ArrayList;
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

	@Override
	public int createUser(User user) {
		int id = (int)sessionFactory.getCurrentSession()
				.save(user);
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUser(String username) { //TODO incorporate password
		List<User> users = new ArrayList<User>();
		String hql = "FROM User WHERE username=?";
		users = (List<User>) sessionFactory.getCurrentSession()
				.createQuery(hql)
				.setParameter(0, username)
				.getResultList();

		if (users.size() > 0) {
	//		System.out.println(users.get(0));
			return users.get(0);
			
		} else {
			return null;
		}
	}
	
	
	
}