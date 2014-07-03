package org.scripps.branch.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQueries;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.scripps.branch.config.ApplicationContext;
import org.scripps.branch.entity.Attribute;
import org.scripps.branch.entity.Feature;
import org.scripps.branch.entity.Weka;
import org.scripps.branch.repository.AttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weka.core.Instances;

public class AttributeService{

//	public static EntityManagerFactory emf = Persistence
//			.createEntityManagerFactory("DEFAULTJPA");
	
//	@PersistenceContext
//	private EntityManagerFactory emf;
//    private EntityManager em;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AttributeService.class);

	@Autowired
	AttributeRepository attr;
    
//	public Attribute getByAttNameDataset(String att_name, String dataset) {
//		EntityManager em = emf.createEntityManager();
//		int counter = 0;
//		Attribute attributeObject = new Attribute();
//		try {
//			String query = "select A from Attribute A where A.name='"
//					+ att_name + "' and A.dataset = '" + dataset + "'";
//			em.getTransaction().begin();
//			Query q = em.createQuery(query);
//
//			List<?> list = q.getResultList();
//
//			Iterator<?> it = list.iterator();
//
//			while (it.hasNext()) {
//
//				attributeObject = (Attribute) it.next();
//				counter++;
//				LOGGER.debug("AttributeObject" + attributeObject.toString());
//			}
//			LOGGER.debug("Counter =" + counter);
//			em.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return attributeObject;
//	}
//
//	public List<Attribute> getByFeatureId(String db_Id) {
//		EntityManager em = emf.createEntityManager();
//		List<Attribute> atts = new ArrayList<Attribute>();
//		Attribute attributeObject = new Attribute();
//		int counter = 0;
//		try {
//
//			String query = "select A from Attribute A where A.feature=" + db_Id;
//			em.getTransaction().begin();
//			Query q = em.createQuery(query);
//			List<?> list = q.getResultList();
//			Iterator<?> it = list.iterator();
//			while (it.hasNext()) {
//
//				attributeObject = (Attribute) it.next();
//				atts.add(attributeObject);
//				counter++;
//
//				LOGGER.debug("Attribute Object" + attributeObject.toString());
//			}
//			LOGGER.debug("Counter =" + counter);
//			em.getTransaction().commit();
//			em.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return atts;
//	}
//	
	@Transactional
	public List<Attribute> getByFeatureUniqueId(String Unique_Id,
			String dataset) {
			return attr.findByFeatureUniqueId(Unique_Id, "metabric_with_clinical");
		}
	
	public static void main(String args[]) throws Exception {
		AttributeService a = new AttributeService();
		List<Attribute> ListOfAb = a.getByFeatureUniqueId("1960", "metabric_with_clinical");
	}

//	public void load(String dataset_Name, String weka_Data,
//			String attribute_Info_File) throws Exception {
//		EntityManager em = emf.createEntityManager();
//		Map<String, String> att_uni = new HashMap<String, String>();
//
//		int counter = 0;
//		FileReader attInfoFile;
//		BufferedReader attInfoBuff;
//
//		String row, attribute, uniqueId;
//		try {
//
//			attInfoFile = new FileReader(attribute_Info_File);
//			attInfoBuff = new BufferedReader(attInfoFile);
//
//			String header = attInfoBuff.readLine();
//
//			while ((row = attInfoBuff.readLine()) != null) {
//
//				String[] keyValPair = row.split("\t");
//
//				attribute = keyValPair[0];
//				uniqueId = keyValPair[2];
//				att_uni.put(attribute, uniqueId);
//				counter++;
//
//				System.out.println(attribute + "\t" + uniqueId);
//
//			}
//
//			LOGGER.debug("Number of Rows :" + counter);
//			LOGGER.debug("Header of the file contents" + header);
//
//		} catch (FileNotFoundException e) {
//			LOGGER.debug("File not found: AttributeInfoFile", e);
//			e.printStackTrace();
//		} catch (IOException e) {
//			LOGGER.debug("Unable to Read File", e);
//			e.printStackTrace();
//		}
//
//		try {
//
//			Weka wekaObj = new Weka();
//			wekaObj.buildWeka(new FileInputStream(weka_Data), null,
//					dataset_Name);
//			Instances data = wekaObj.getTrain();
//			LOGGER.debug("Instances: " + data.numInstances());
//			Feature featureObj = null;
//			int flag = 0;
//
//			for (int i = 0; i < data.numAttributes(); i++) {
//				weka.core.Attribute att = data.attribute(i);
//				String name = att.name();
//				int col = att.index();
//				String unique_id = att_uni.get(name);
//				if (att.index() != data.classIndex()) {
//					Long feat_id = (long) -1;
//					if (unique_id != null) {
//						Feature feat = FeatureService.getByUniqueId(unique_id);
//						if (feat == null) {
//							Attribute attrObj = getByAttNameDataset(name, dataset_Name);
//							if (attrObj != null) {
//								feat = FeatureService.getByDbId(attrObj
//										.getFeature().getId());
//							}
//						}
//						feat_id = feat.getId();
//					} else {
//
//						em.getTransaction().begin();
//						LOGGER.debug("No feature in mapping table for " + name
//								+ " adding generic");
//						featureObj = new FeatureService();
//						featureObj.setUnique_id(dataset_Name + "_"
//								+ att.index());
//						featureObj.setShort_name(att.name());
//						featureObj.setLong_name(att.name());
//						featureObj.setDescription("");
//
//						// if(em.contains(featureObj)){
//						//
//						// em.persist(featureObj);
//						// flag=1;
//						// }
//						// else{
//						// em.merge(featureObj);
//						// flag=2;
//						// }
//						em.persist(featureObj);
//						em.getTransaction().commit();
//						LOGGER.debug("Entity Flag Value = " + flag);
//					}
//					// error chances
//					if (!em.contains(featureObj)) {
//						Attribute aObj = new Attribute();
//						aObj.setCol_index(col);
//						aObj.setDataset(dataset_Name);
//						aObj.setFeature(featureObj);
//					}
//
//				}
//			}
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}