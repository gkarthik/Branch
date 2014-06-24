package Tests;



import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.hibernate.jpa.internal.EntityManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.PersistenceProvider;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Config.PersistenceContext;
import DAO.UserRepositoryService;



@Component
public class TestHibernateInsert {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestHibernateInsert.class);
	
	@javax.persistence.PersistenceContext
	public static EntityManager em;
	
	


	/*
	 * 
		public static void main(String[] args) 
	    {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        session.beginTransaction();

	        //Add new Employee object
	        Test emp = new Test();

	        emp.setName("Vyshakh");



	        //Save the employee in database
	        session.save(emp);

	        //Commit the transaction
	        session.getTransaction().commit();
	        HibernateUtil.shutdown();
	    }
	 */

	@Transactional
	public static void main(String[] args) {


	
		LOGGER.debug("Check the transaction",em );

		em.getTransaction().begin();


		Test T =new Test();

		T.setName("Vyshakh");

		em.persist(T);
		em.getTransaction().commit();



	}



}









