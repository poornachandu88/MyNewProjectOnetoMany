package com.poorna.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poorna.model.CreditCard;
import com.poorna.model.Person;


@Service
@Repository
public class PersonService {

	protected static Logger logger = Logger.getLogger("service");
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Transactional
	public List<Person> getAll() {
		logger.debug("Retrieving all persons");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Person");
		
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Retrieves a single person
	 */
	@Transactional
	public Person get( Integer id ) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
		// Retrieve existing person
		return (Person) session.get(Person.class, id);
	}
	
	/**
	 * Adds a new person
	 */
	@Transactional
	public void add(Person person) {
		logger.debug("Adding new person");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
		// Persists to db
		session.save(person);
	}
	
	/**
	 * Deletes an existing person
	 * @param id the id of the existing person
	 */
	@Transactional
	public void delete(Integer id) {
		logger.debug("Deleting existing person");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
	
		// Retrieve record
		Person person = (Person) session.get(Person.class, id);
		
		// Delete person
		session.delete(person);
	}
	
	/**
	 * Edits an existing person
	 */
	@Transactional
	public void edit(Person person) {
		logger.debug("Editing existing person");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
		// Retrieve existing person via id
		Person existingPerson = (Person) session.get(Person.class, person.getId());
		
		// Assign updated values to this person
		existingPerson.setFirstName(person.getFirstName());
		existingPerson.setLastName(person.getLastName());
		existingPerson.setMoney(person.getMoney());

		// Save updates
		session.save(existingPerson);
	}
}
