package com.freekaba.util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ModelUtil {
	@Autowired
    private SessionFactory sessionFactory;
  
	public <T> Serializable create(final T entity) {
    	System.out.println(sessionFactory);
        return sessionFactory.getCurrentSession().save(entity);        
    }
	
	public <T> T saveOrUpdate(final T entity) {
    	System.out.println(sessionFactory);
    	sessionFactory.getCurrentSession().saveOrUpdate(entity);
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
    
    @SuppressWarnings("unchecked")  
    public <T> List<T> fetchAll(Class<T> entityClass) {        
        return sessionFactory.getCurrentSession().createQuery(" FROM "+entityClass.getName()).getResultList();        
    }
  
    @SuppressWarnings("rawtypes")
    public <T> List fetchAll(String query) {        
        return sessionFactory.getCurrentSession().createQuery(query).getResultList();        
    }
    
    public <T> T fetchById(Serializable id, Class<T> entityClass) {
        return (T)sessionFactory.getCurrentSession().get(entityClass, id);
    }   
    
    
}
