package com.internal.tasks.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.internal.tasks.beans.ResponseRestFulWS;
import com.internal.tasks.util.HibernateUtil;

public class ResponseRestFulWSDAO{
	
	public ResponseRestFulWS read(String name) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Criteria criteria = session.createCriteria(ResponseRestFulWS.class);
		ResponseRestFulWS item = (ResponseRestFulWS) criteria.add(Restrictions.eq("name", name)).uniqueResult();
				
		session.close();
		
		return item;
	}

	public ResponseRestFulWS save(ResponseRestFulWS object) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();
		
		Long id_name = (Long) session.save(object);
		object.setName_id(id_name);

		session.getTransaction().commit();

		session.close();

		return object;
	}
	
	public List<ResponseRestFulWS> saveCollection(List<ResponseRestFulWS> list) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		session.beginTransaction();
		
		for(ResponseRestFulWS item : list){
			
		Long id_name = (Long) session.save(item);
		item.setName_id(id_name);
		
		}
		
		session.getTransaction().commit();
		
		session.close();
		
		return list;
	}


	public ResponseRestFulWS update(ResponseRestFulWS object) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.merge(object);

		session.getTransaction().commit();

		session.close();
		return object;

	}

	public void delete(ResponseRestFulWS output) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.delete(output);

		session.getTransaction().commit();

		session.close();
	}

}
