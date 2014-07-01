package WekaDataBuilder;

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
///import org.scripps.combo.model.Feature;
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

import WekaDataTables.AttributeDB;
import WekaDataTables.FeatureDB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FeatureBuilder extends FeatureDB {

	public static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("DEFAULTJPA");
	public static EntityManager em = emf.createEntityManager();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FeatureBuilder.class);

	private List<AttributeDB> dataset_attributes;

	public List<AttributeDB> getDataset_attributes() {
		return dataset_attributes;
	}

	public void setDataset_attributes(List<AttributeDB> dataset_attributes) {
		this.dataset_attributes = dataset_attributes;
	}

	// From Feature Table: public static Map<String, Feature>
	// getByDataset(String dataset, boolean load_annotations_very_slowly){
	public static Map<String, FeatureDB> getByDataset(String dataset,
			boolean load_annotations_very_slowly) {

		Map<String, FeatureDB> features = new HashMap<String, FeatureDB>();

		FeatureBuilder fb = new FeatureBuilder();

		String query = "select f.id,f.unique_id,f.short_name,f.long_name,f.description,f.created,f.updated,"
				+ "a.id,a.col_index,a.name,a.dataset,a.relieff,a.created,a.updated,a.feature"
				+ " from FeatureDB f, AttributeDB a "
				+ "where a.dataset = '"
				+ dataset + "'and f.id = feature_id";

		try {

			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();
			LOGGER.debug("Feature Counter =");
			Iterator<?> it = list.iterator();
			int featureCounter = 0, attributeCounter = 0;

			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();

				FeatureDB featureObject = features.get(result[1]);// check for
																	// f.uniqueid

				if (featureObject == null) {

					featureObject = new FeatureDB((Long) result[0],
							(String) result[1], (String) result[2],
							(String) result[3], (String) result[4],
							(DateTime) result[5], (DateTime) result[6]);
					featureCounter++;

				}

				LOGGER.debug("Feature Counter : " + featureCounter);

				List<AttributeDB> atts = fb.getDataset_attributes();
				if (atts == null) {

					atts = new ArrayList<AttributeDB>();
				}
				AttributeDB attributeObject = new AttributeDB((int) result[7],
						(int) result[8], (String) result[9],
						(String) result[10], (float) result[11],
						(DateTime) result[12], (DateTime) result[13]);

				attributeObject.setFeature((FeatureDB) result[14]);

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

	// From FeatureTable: public static Feature getByDbId(int id)
	public static FeatureDB getByDbId(long id) {

		FeatureDB featureObject = null;

		String query = "select f.id,f.unique_id,f.short_name,"
				+ "f.long_name,f.description,f.created,f.updated"
				+ " from FeatureDB f where f.id =" + id;

		try {
			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();

			Iterator<?> it = list.iterator();
			int featureCounter = 0;

			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();

				featureObject = new FeatureDB();
				featureObject = new FeatureDB((Long) result[0],
						(String) result[1], (String) result[2],
						(String) result[3], (String) result[4],
						(DateTime) result[5], (DateTime) result[6]);
				featureCounter++;
			}

			LOGGER.debug("FeatureCounter for getbyuniqueId:" + featureCounter);
			LOGGER.debug("FeatureObject :" + featureObject.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return featureObject;

	}

	// From FeatureTable: public static Feature getByUniqueId(String unique_id)
	public static FeatureDB getByUniqueId(String unique_id) {

		FeatureDB featureObject = null;

		String query = "select f.id,f.unique_id,f.short_name,"
				+ "f.long_name,f.description,f.created,f.updated"
				+ " from FeatureDB f where f.unique_id ='" + unique_id + "'";

		try {
			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();

			Iterator<?> it = list.iterator();
			int featureCounter = 0;

			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();

				featureObject = new FeatureDB((Long) result[0],
						(String) result[1], (String) result[2],
						(String) result[3], (String) result[4],
						(DateTime) result[5], (DateTime) result[6]);
				featureCounter++;
			}

			LOGGER.debug("FeatureCounter for getbyuniqueId:" + featureCounter);
			LOGGER.debug("FeatureObject :" + featureObject.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return featureObject;

	}

	public static ObjectNode getMetaBricClinicalFeatures(ObjectMapper mapper) {

		ObjectNode featureObject = mapper.createObjectNode();
		String query = "select f.id,f.unique_id,f.short_name,"
				+ "f.long_name,f.description"
				+ " from FeatureDB f, AttributeDB a "
				+ "where a.dataset='metabric_with_clinical' and "
				+ "f.id = feature_id and " + "f.unique_id like 'metabric%'";

		try {

			em.getTransaction().begin();
			Query q = em.createQuery(query);
			List<?> list = q.getResultList();
			Iterator<?> it = list.iterator();
			int featureCounter = 0;
			ArrayNode featureArrayNode = mapper.createArrayNode();

			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();

				ObjectNode fObj = mapper.createObjectNode();

				fObj.put("id", (long) result[0]);
				fObj.put("unique_id", (String) result[1]);
				fObj.put("short_name", (String) result[2]);
				fObj.put("long_name", (String) result[3]);
				fObj.put("description", (String) result[4]);
				featureArrayNode.add(fObj);

				featureCounter++;
			}

			featureObject.put("features", featureArrayNode);
			em.getTransaction().commit();
			em.close();
			LOGGER.debug("FeatureCounter for getMetaBricClinicalFeatures :"
					+ featureCounter);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return featureObject;
	}

	// load feature table with data from Homosapiens_gene.info
	public static void loadFeatureTable() {
		int res = 0;
		em.getTransaction().begin();
		String hql = "Insert into FeatureDB (unique_id,short_name,long_name,description)"
				+ "select geneid,symbol,full_name_from_nomenclature_authority,description from Homosapiens_Gene_Info";
		Query q = em.createQuery(hql);
		res = q.executeUpdate();

		System.out.println("Rows affected: " + res);

		em.getTransaction().commit();
		em.close();

	}

	public static void main(String args[]) {

		// loadFeatureTable();
		// / FeatureBuilder a =new FeatureBuilder();
		// FeatureBuilder.getByUniqueId("17");
		// / FeatureBuilder.getByDbId(1);
		// FeatureBuilder.getByDataset("dream_breast_cancer", false);

		 ObjectMapper mapper = new ObjectMapper();
		 ObjectNode features =
		 FeatureBuilder.getMetaBricClinicalFeatures(mapper);
		 String json_features;
		 try {
		 json_features = mapper.writeValueAsString(features);
		 System.out.println(json_features);
		 } catch (JsonProcessingException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		

	}

}
