package WekaDataBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import WekaDataTables.AttributeDB;
import WekaDataTables.FeatureDB;

import org.joda.time.DateTime;

import weka.core.Attribute;
import weka.core.Instances;

public class AttributeBuilder extends AttributeDB {

	public static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("DEFAULTJPA");
	public static EntityManager em = emf.createEntityManager();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AttributeBuilder.class);

	//Function load from Attribute.java : public static void load(String dataset_name, String weka_data, String att_info_file) throws Exception {

	public void load(String dataset_Name, String weka_Data,
			String attribute_Info_File) throws Exception {

		Map<String, String> att_uni = new HashMap<String, String>();

		String header;
		int counter = 0;
		FileReader attInfoFile;
		BufferedReader attInfoBuff;

		String row, attribute, uniqueId;
		try {

			attInfoFile = new FileReader(attribute_Info_File);
			attInfoBuff = new BufferedReader(attInfoFile);

			header = attInfoBuff.readLine();

			while ((row = attInfoBuff.readLine()) != null) {

				String[] keyValPair = row.split("\t");

				attribute = keyValPair[0];
				uniqueId = keyValPair[2];
				att_uni.put(attribute, uniqueId);
				counter++;

				System.out.println(attribute + "\t" + uniqueId);

			}

			System.out.println("Number of Rows :" + counter);

		} catch (FileNotFoundException e) {
			LOGGER.debug("File not found: AttributeInfoFile", e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.debug("Unable to Read File", e);
			e.printStackTrace();
		}

		try {

			WekaCutomizer wekaObj = new WekaCutomizer();
			wekaObj.buildWeka(new FileInputStream(weka_Data), null, dataset_Name);
			Instances data = wekaObj.getTrain();
			FeatureDB featureObj=null;
			int flag=0;

			for(int i=0;i<data.numAttributes();i++){
				Attribute att=data.attribute(i);
				String name = att.name();
				int col =att.index();
				String unique_id=att_uni.get(name);
				if(att.index()!=data.classIndex()){
					Long feat_id=(long) -1;
					if(unique_id!=null){
						FeatureDB feat =FeatureBuilder.getByUniqueId(unique_id);
						if(feat==null){
							AttributeDB attrObj =AttributeBuilder.getByAttNameDataset(name,dataset_Name);
							if(attrObj!=null){
								feat=FeatureBuilder.getByDbId(attrObj.getFeaturedb().getId());
							}
						}

						feat_id=feat.getId();
					}
					else{

						em.getTransaction().begin();
						LOGGER.debug("No feature in mapping table for "+name+" adding generic");
						featureObj=new FeatureBuilder();
						featureObj.setUnique_id(dataset_Name+""+att.index());
						featureObj.setShort_name(att.name());
						featureObj.setLong_name(att.name());
						featureObj.setDescription("");

						if(em.contains(featureObj)){

							em.persist(featureObj);
							flag=1;
						}
						else{
							em.merge(featureObj);
							flag=2;
						}
						em.getTransaction().commit();
						LOGGER.debug("Entity Flag Value = " +flag);
					}
					if(flag==0){
						AttributeDB aObj =  new AttributeDB();
						aObj.setCol_index(col);
						aObj.setDataset(dataset_Name);
						aObj.setFeature_id(featureObj);
					}

				}
			}

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


	}

	//
	public static AttributeDB getByAttNameDataset(String att_name, String dataset){

		int counter = 0;

		AttributeDB attributeObject=new AttributeDB();
		try {
			String query = "select col_index,created, dataset,name,relieff,updated "
					+ "from AttributeDB a where name='"+att_name+"' and dataset = '"+dataset+"'";


			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();

			Iterator<?> it = list.iterator();

			while (it.hasNext()) {

				attributeObject = new AttributeDB();

				Object[] result = (Object[]) it.next();

				attributeObject.setCol_index((int) result[0]);
				attributeObject.setCreated((DateTime) result[1]);
				attributeObject.setDataset((String) result[2]);
				//attributeObject.setFeaturedb(attributeObject);//((long) result[3]);
				
				attributeObject.setName((String) result[4]);
				attributeObject.setReliefF((float) result[5]);
				attributeObject.setUpdated((DateTime) result[6]);


				System.out.println(counter);
				counter++;
				LOGGER.debug("Counter =" + counter);
				
				

			}

			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return attributeObject;
	}




//
	public static List<AttributeDB> getByFeatureDbId(String db_Id) {

		List<AttributeDB> atts = new ArrayList<AttributeDB>();

		int counter = 0;
		try {
			String query = "select col_index,created, dataset,feature_id,name,relieff,updated from AttributeDB where feature_id="
					+ db_Id;

			em.getTransaction().begin();
			Query q = em.createQuery(query);

			List<?> list = q.getResultList();

			Iterator<?> it = list.iterator();

			while (it.hasNext()) {

				AttributeDB attributeObject = new AttributeDB();

				Object[] result = (Object[]) it.next();

				attributeObject.setCol_index((int) result[0]);
				attributeObject.setCreated((DateTime) result[1]);
				attributeObject.setDataset((String) result[2]);
		//		attributeObject.setFeature_id((int) result[3]);
				attributeObject.setName((String) result[4]);
				attributeObject.setReliefF((float) result[5]);
				attributeObject.setUpdated((DateTime) result[6]);

				atts.add(attributeObject);
				System.out.println(counter);
				counter++;
				LOGGER.debug("Counter =" + counter);

			}

			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return atts;
	}

	public static void main(String args[]) {

		AttributeBuilder AB = new AttributeBuilder();
		// AB.load(null, null,
		// "/home/bob/workspace/BranchBio/src/main/resources/WekaFiles/Oslo_mapping.txt");
		//AttributeBuilder.getByFeatureDbId("12");
		AttributeBuilder.getByAttNameDataset("Vyshakh", "newdataset");
	}

}

// System.out.println((int)result[0]);
// System.out.println((DateTime) result[1]);
// System.out.println((String)result[2]);
// System.out.println((int)result[3]);
//
// System.out.println((String)result[4]);
// System.out.println((float)result[5]);
// System.out.println((DateTime) result[6]);
//
