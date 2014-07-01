package org.scripps.branch.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;






//import WekaDataBuilder.AttributeBuilder;
//
//import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.joda.time.DateTime;
import org.scripps.branch.entity.Attribute;
import org.scripps.branch.entity.Feature;
///import org.scripps.combo.model.Feature;
//import org.scripps.combo.model.Feature;
//import org.scripps.util.JdbcConnection;
//import org.scripps.combo.model.Feature;
//import org.scripps.combo.model.Attribute;
//import org.scripps.combo.model.Feature;
//import org.scripps.util.JdbcConnection;
//import org.scripps.combo.model.Annotation;
//import org.scripps.combo.model.Attribute;
//import org.scripps.combo.model.TextAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureService extends Feature {

	public static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("DEFAULTJPA");
	public static EntityManager em = emf.createEntityManager();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FeatureService.class);

	private List<Attribute> dataset_attributes;

	public List<Attribute> getDataset_attributes() {
		return dataset_attributes;
	}

	public void setDataset_attributes(List<Attribute> dataset_attributes) {
		this.dataset_attributes = dataset_attributes;
	}

	// load feature table with data from Homosapiens_gene.info
	public static void loadFeatureTable() {
		int res = 0;
		em.getTransaction().begin();
		String hql = "Insert into FeatureDB (unique_id,short_name,long_name,description)"
				+ "select geneid,symbol,full_name_from_nomenclature_authority,description   from Homosapiens_Gene_Info";
		Query q = em.createQuery(hql);
		res = q.executeUpdate();

		System.out.println("Rows affected: " + res);

		em.getTransaction().commit();
		em.close();

	}

	// From Feature Table: public static Map<String, Feature>
	// getByDataset(String dataset, boolean load_annotations_very_slowly){
	public static Map<String, FeatureService> getByDataset(String dataset,
			boolean load_annotations_very_slowly) {

		int counter = 0;
		Map<String, FeatureService> features = new HashMap<String, FeatureService>();

		FeatureService fb = new FeatureService();

		String query = "select f.id,"
				+ "f.unique_id,"
				+ "f.short_name,"
				+ "f.long_name,"
				+ "f.description,"
				+ "f.created,"
				+ "f.updated,"
				+ "a.id,"
				+ "a.col_index,"
				+ "a.created,"
				+ "a.dataset,"
				+ "a.feature,"
				+ "a.name,"
				+ "a.relieff,"
				+ "a.updated"								//a.feature_id,
				+ " from FeatureDB f, AttributeDB a " + "where a.dataset = '"
				+ dataset + "' and f.id = a.feature.id";
		
		System.out.println(query);
			
				

		try {

			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();

			Iterator<?> it = list.iterator();
			int featureCounter = 0, attributeCounter = 0;

			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();

				FeatureService featureObject = features.get(result[1]);// check for
				// f.uniqueid

				if (featureObject == null) {

					featureObject = new FeatureService();
					featureObject.setId((Long) result[0]);
					featureObject.setUnique_id((String) result[1]);
					featureObject.setShort_name((String) result[2]);
					featureObject.setLong_name((String) result[3]);
					featureObject.setDescription((String) result[4]);
					featureObject.setCreated((DateTime) result[5]);
					featureObject.setUpdated((DateTime) result[6]);

					System.out.println("fea" + result[0]);
					System.out.println("fea" + result[5]);
					System.out.println("fea" + (String) result[4]);
					// System.out.println((int)result[3]);

					System.out.println("fea" + (String) result[4]);
					// System.out.println((float)result[5]);
					System.out.println("fea" + result[6]);
					//
					featureCounter++;

				}

				List<Attribute> atts = fb.getDataset_attributes();
				if (atts == null) {

					atts = new ArrayList<Attribute>();
				}
				Attribute a = new Attribute();
				a.setId((int) result[7]);
				a.setCol_index((int) result[8]);
				a.setCreated((DateTime) result[9]);
				a.setDataset((String) result[10]);
				FeatureService fObject =new FeatureService();
				fObject = getByUniqueId((String) result[1]);
			//	a.setFeature_id((Long)result[11]);
				//a.setFeature_id();(featureObject);
				a.setFeature(fObject);
				
				a.setName((String) result[12]);
				a.setReliefF((float) result[13]);
				a.setUpdated((DateTime) result[14]);
				attributeCounter++;
				if (!atts.contains(a)) {
					atts.add(a);
					fb.setDataset_attributes(atts);
				}
				if (load_annotations_very_slowly) {

					// f.getAnnotationsFromDb();
					// f.getTextAnnotationsFromDb();

				}

				features.put(featureObject.getUnique_id(), featureObject);

				System.out.println("att" + (int) result[8]);
				System.out.println("att" + result[9]);
				System.out.println("att" + (String) result[10]);
				System.out.println("unique" + a.getFeature_id());
				

				System.out.println("FeatureID" +a.getFeature_id().getId());
				System.out.println("FeatureID" + result[11]);
				System.out.println("att" + (String) result[12]);
				System.out.println("att" + (float) result[13]);
				System.out.println("att" + result[14]);

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

	//From FeatureTable: public static Feature getByUniqueId(String unique_id)
	public static FeatureService getByUniqueId(String unique_id){

		FeatureService featureObject =null;

		String query = "select f.id,f.unique_id,f.short_name,"
				+ "f.long_name,f.description,f.created,f.updated"
				+ " from FeatureDB f where f.unique_id ='"
				+ unique_id+"'";

		try {
			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();

			Iterator<?> it = list.iterator();
			int featureCounter = 0;

			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();

				featureObject = new FeatureService();
				featureObject.setId((Long) result[0]);
				featureObject.setUnique_id((String) result[1]);
				featureObject.setShort_name((String) result[2]);
				featureObject.setLong_name((String) result[3]);
				featureObject.setDescription((String) result[4]);
				featureObject.setCreated((DateTime) result[5]);
				featureObject.setUpdated((DateTime) result[6]);
				featureCounter++;
			}

			LOGGER.debug("FeatureCounter for getbyuniqueId:"+featureCounter);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return featureObject;

	}

	//From FeatureTable: public static Feature getByDbId(int id)
	public static FeatureService getByDbId(long id){


		FeatureService featureObject =null;

		String query = "select f.id,f.unique_id,f.short_name,"
				+ "f.long_name,f.description,f.created,f.updated"
				+ " from FeatureDB f where f.id ="
				+ (long)id;

		try {
			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();

			Iterator<?> it = list.iterator();
			int featureCounter = 0;

			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();

				featureObject = new FeatureService();
				featureObject.setId((Long) result[0]);
				//			featureObject.setUnique_id((String) result[1]);
				featureObject.setShort_name((String) result[2]);
				featureObject.setLong_name((String) result[3]);
				featureObject.setDescription((String) result[4]);
				featureObject.setCreated((DateTime) result[5]);
				featureObject.setUpdated((DateTime) result[6]);
				featureCounter++;
			}

			LOGGER.debug("FeatureCounter for getbyuniqueId:"+featureCounter);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return featureObject;

	}

	//From FeatureTable:public int insert() throws SQLException



	public static void main(String args[]) {

		//loadFeatureTable();	
		//getByDataset("newset", false);
		FeatureService a = getByUniqueId("17");
		System.out.println(a.getAttributes());
		//getByDbId(1);
	}
}
