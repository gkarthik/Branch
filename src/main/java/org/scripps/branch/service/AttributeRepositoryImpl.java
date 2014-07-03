package org.scripps.branch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.scripps.branch.entity.Attribute;
import org.scripps.branch.repository.AttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

@Repository
public class AttributeRepositoryImpl implements AttributeRepository {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AttributeService.class);
	
	private EntityManager em;
	
	@PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }
	
	@Transactional
	public List<Attribute> findByFeatureUniqueId(String unique_id, String dataset) {
		List<Attribute> atts = new ArrayList<Attribute>();
		int counter = 0;
		Attribute attributeObject = new Attribute();
		try {
			String query = "select A from Attribute A, Feature F where A.feature=F.id and F.unique_id='"
					+ unique_id + "' and A.dataset='" + dataset + "'";
			LOGGER.debug(query);
			System.out.println(em==null);
			Query q = em.createQuery(query);
			List<?> list = q.getResultList();
			Iterator<?> it = list.iterator();

			while (it.hasNext()) {
				attributeObject = (Attribute) it.next();
				atts.add(attributeObject);
				counter++;
				LOGGER.debug("Attribute Object" + attributeObject.toString());
			}
			LOGGER.debug("Counter =" + counter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return atts;
	}
}
