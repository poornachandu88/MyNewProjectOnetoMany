package com.poorna.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poorna.model.CreditCard;
import com.poorna.model.Person;

@Service
@Repository
public class CreditCardService {

	protected static Logger logger = Logger.getLogger("service");
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<CreditCard> getAll(Integer personId) {
		logger.debug("Retrieving all credit cards");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Person as p WHERE p.id="+personId);
		
		Person person = (Person) query.uniqueResult();
		
		// Retrieve all
		return  new ArrayList<CreditCard>(person.getCreditCards());
	}
	
	@Transactional
	public List<CreditCard> getAll() {
		logger.debug("Retrieving all credit cards");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM CreditCard");
		
		// Retrieve all
		return  query.list();
	}
	
	@Transactional
	public CreditCard get( Integer id ) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
		// Retrieve existing credit card
		CreditCard creditCard = (CreditCard) session.get(CreditCard.class, id);
		
		// Persists to db
		return creditCard;
	}
	
	@Transactional
	public void add(Integer personId, CreditCard creditCard) {
		logger.debug("Adding new credit card");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
	
		// Persists to db
		session.save(creditCard);
		
		// Add to person as well
		// Retrieve existing person via id
		Person existingPerson = (Person) session.get(Person.class, personId);
		
		// Assign updated values to this person
		existingPerson.getCreditCards().add(creditCard);

		// Save updates
		session.save(existingPerson);
	}
	
	@Transactional
	public void delete(Integer id) {
		logger.debug("Deleting existing credit card");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
    	// Delete reference to foreign key credit card first
		// We need a SQL query instead of HQL query here to access the third table
    	Query query = session.createSQLQuery("DELETE FROM PersonCreditCards " +
    			"WHERE CREDIT_ID="+id);
    	
    	query.executeUpdate();
    	
		// Retrieve existing credit card
		CreditCard creditCard = (CreditCard) session.get(CreditCard.class, id);
		
		// Delete 
		session.delete(creditCard);
	}
	
	@Transactional
	public void edit(CreditCard creditCard) {
		logger.debug("Editing existing creditCard");
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		
		// Retrieve existing credit card via id
		CreditCard existingCreditCard = (CreditCard) session.get(CreditCard.class, creditCard.getId());
		
		// Assign updated values to this credit card
		existingCreditCard.setNumber(creditCard.getNumber());
		existingCreditCard.setType(creditCard.getType());

		// Save updates
		session.save(existingCreditCard);
	}
}
