package org.scripps.branch.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.scripps.branch.entity.Attribute;
import org.scripps.branch.entity.Feature;
import org.scripps.branch.service.FeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class FeatureRepositoryImpl implements FeatureRepository {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FeatureRepositoryImpl.class);
	
	protected EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
		LOGGER.debug("entity manager set from Impl!");
	}
	
	public void getEntityManager(EntityManager em) {
		this.em = em;
		LOGGER.debug("get!");
	}
	
	@Override
	public Map<String, Feature> findByDataset(String dataset, boolean load_annotations_very_slowly) {

			Map<String, Feature> features = new HashMap<String, Feature>();

			FeatureService fb = new FeatureService();

			String query = "select f.id,f.unique_id,f.short_name,f.long_name,f.description,f.created,f.updated,"
					+ "a.id,a.col_index,a.name,a.dataset,a.relieff,a.created,a.updated,a.feature"
					+ " from Feature f, Attribute a "
					+ "where a.dataset = '"
					+ dataset + "'and f.id = feature_id";

			try {
				Query q = em.createQuery(query);

				List<?> list = q.getResultList();
				LOGGER.debug("Feature Counter =");
				Iterator<?> it = list.iterator();
				int featureCounter = 0, attributeCounter = 0;

				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();

					Feature featureObject = features.get(result[1]);// check for
																	// f.uniqueid

					if (featureObject == null) {

						featureObject = new Feature((Long) result[0],
								(String) result[1], (String) result[2],
								(String) result[3], (String) result[4],
								(DateTime) result[5], (DateTime) result[6]);
						featureCounter++;

					}

					LOGGER.debug("Feature Counter : " + featureCounter);

					List<Attribute> atts = fb.getDataset_attributes();
					if (atts == null) {

						atts = new ArrayList<Attribute>();
					}
					Attribute attributeObject = new Attribute((int) result[7],
							(int) result[8], (String) result[9],
							(String) result[10], (float) result[11],
							(DateTime) result[12], (DateTime) result[13]);

					attributeObject.setFeature((Feature) result[14]);

					attributeCounter++;
					if (!atts.contains(attributeObject)) {
						atts.add(attributeObject);
						fb.setDataset_attributes(atts);
					}
					if (load_annotations_very_slowly) {

						// f.getAnnotationsFromDb();
						// f.getTextAnnotationsFromDb();

					}

					features.put(featureObject.getUnique_id(), featureObject);

					LOGGER.debug("FeatureObj: " + featureObject.toString());
					LOGGER.debug("AttributeObj: " + attributeObject.toString());

				}
				LOGGER.debug("Feature Counter =" + featureCounter);
				LOGGER.debug("Attribute Counter =" + attributeCounter);

				em.getTransaction().commit();
				em.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return features;
		}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Attribute arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Attribute> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Attribute> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute findOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Attribute> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(Iterable<Attribute> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Attribute> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attribute saveAndFlush(Attribute arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Attribute> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attribute> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Attribute> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}