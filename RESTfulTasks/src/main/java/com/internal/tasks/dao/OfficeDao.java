package com.internal.tasks.dao;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.google.gson.JsonSyntaxException;
import com.internal.tasks.beans.Office;
import com.internal.tasks.util.HibernateUtil;

public class OfficeDao {

	public List<Office> read(String name, String operation) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Criteria criteria = session.createCriteria(Office.class);
		List<Office> item = (List<Office>) criteria
				.add(Restrictions.and(Restrictions.eq("name", name), Restrictions.eq("operation", operation))).list();

		session.close();

		return item;
	}
	
	public List<Office> readListById(List<Long> ids, String operation) throws JsonSyntaxException, UnsupportedEncodingException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		Criteria criteria = session.createCriteria(Office.class);

		List<Office> item = (List<Office>) criteria.add(Restrictions.in("name_id",ids)).list();
		
		session.close();
		
		return item;
	}
	
	public List<Office> readListByName(List<String> names, String operation) throws JsonSyntaxException, UnsupportedEncodingException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		Criteria criteria = session.createCriteria(Office.class);
		
		List<Office> item = (List<Office>) criteria.add(Restrictions.and(Restrictions.in("name",names),Restrictions.eq("operation", operation))).list();
		
		session.close();
		
		return item;
	}

	public Office save(Office object) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		Long id_name = (Long) session.save(object);
		object.setName_id(id_name);

		session.getTransaction().commit();

		session.close();

		return object;
	}

	public List<Office> saveCollection(List<Office> list) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		for (Office item : list) {

			Long id_name = (Long) session.save(item);
			item.setName_id(id_name);

		}

		session.getTransaction().commit();

		session.close();

		return list;
	}

	public Office update(Office object) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.merge(object);

		session.getTransaction().commit();

		session.close();
		return object;

	}

	public void delete(Office output) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.delete(output);

		session.getTransaction().commit();

		session.close();
	}

}
