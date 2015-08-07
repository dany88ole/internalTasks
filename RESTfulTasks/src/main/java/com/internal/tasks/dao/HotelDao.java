package com.internal.tasks.dao;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.google.gson.JsonSyntaxException;
import com.internal.tasks.beans.Hotel;
import com.internal.tasks.util.HibernateUtil;

public class HotelDao {

	public List<Hotel> read(String name, String operation) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Criteria criteria = session.createCriteria(Hotel.class);
		List<Hotel> item = (List<Hotel>) criteria
				.add(Restrictions.and(Restrictions.eq("name", name), Restrictions.eq("operation", operation))).list();

		session.close();

		return item;
	}
	
	public List<Hotel> readListById(List<Long> ids, String operation) throws JsonSyntaxException, UnsupportedEncodingException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		Criteria criteria = session.createCriteria(Hotel.class);

		List<Hotel> item = (List<Hotel>) criteria.add(Restrictions.in("name_id",ids)).list();
		
		session.close();
		
		return item;
	}
	
	public List<Hotel> readListByName(List<String> names, String operation) throws JsonSyntaxException, UnsupportedEncodingException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		Criteria criteria = session.createCriteria(Hotel.class);
		
		List<Hotel> item = (List<Hotel>) criteria.add(Restrictions.in("name",names)).list();
		
		session.close();
		
		return item;
	}

	public Hotel save(Hotel object) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		Long id_name = (Long) session.save(object);
		object.setName_id(id_name);

		session.getTransaction().commit();

		session.close();

		return object;
	}

	public List<Hotel> saveCollection(List<Hotel> list) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		for (Hotel item : list) {

			Long id_name = (Long) session.save(item);
			item.setName_id(id_name);

		}

		session.getTransaction().commit();

		session.close();

		return list;
	}

	public Hotel update(Hotel object) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.merge(object);

		session.getTransaction().commit();

		session.close();
		return object;

	}

	public void delete(Hotel output) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.delete(output);

		session.getTransaction().commit();

		session.close();
	}

}
