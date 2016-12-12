package com.freekaba.util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserUtil {
	@Autowired
    private SessionFactory sessionFactory;
  
	public <T> Serializable create(final T entity) {
        return sessionFactory.getCurrentSession().save(entity);        
    }
	
	public <T> T saveOrUpdate(final T entity) {
    	sessionFactory.getCurrentSession().saveOrUpdate(entity);
		return entity;        
    }
	
	@SuppressWarnings("unchecked")
	public <T> T fetchByUnPw(String username) {
		String hql = "FROM user where username = :username";
    	T entity = (T) sessionFactory.getCurrentSession().createQuery(hql);
		return entity;        
    }
    
    public <T> T update(final T entity) {
        sessionFactory.getCurrentSession().update(entity);   
        return entity;
    }
    
    public <T> void delete(final T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public <T> void delete(Serializable id, Class<T> entityClass) {
        T entity = fetchById(id, entityClass);
        delete(entity);
    }
    
    public <T> T fetchById(Serializable id, Class<T> entityClass) {
        return (T)sessionFactory.getCurrentSession().get(entityClass, id);
    }   
    
    
}
