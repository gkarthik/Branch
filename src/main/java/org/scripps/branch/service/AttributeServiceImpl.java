package org.scripps.branch.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.scripps.branch.entity.Attribute;
import org.scripps.branch.entity.Dataset;
import org.scripps.branch.entity.Feature;
import org.scripps.branch.repository.AttributeRepository;
import org.scripps.branch.repository.FeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import weka.core.Instances;

@Service
public class AttributeServiceImpl implements AttributeService {

	@Autowired
	AttributeRepository attrRepo;

	@Autowired
	FeatureRepository featureRepo;

	@Autowired
	ApplicationContext ctx;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AttributeServiceImpl.class);

	@Override
	public void generateAttributesFromDataset(Instances data, Dataset dataset,
			String inputPath) throws FileNotFoundException {
		List<Attribute> attrList = new ArrayList<Attribute>();
		Attribute attr;
		Feature f;
		HashMap<String, String> mp = getAttributeFeatureMapping(inputPath);
		for (int i = 0; i < data.numAttributes(); i++) {
			attr = new Attribute();
			f = new Feature();
			attr.setName(data.attribute(i).name());
			attr.setCol_index(data.attribute(i).index());
			attr.setDataset(dataset);
			f = featureRepo.findByUniqueId(mp.get(data.attribute(i).name()));
			LOGGER.debug(data.attribute(i).name() + ": "
					+ mp.get(data.attribute(i).name()));
			attr.setFeature(f);
			attrList.add(attr);
		}
		attrRepo.save(attrList);
		attrRepo.flush();
	}

	@Override
	public HashMap<String, String> getAttributeFeatureMapping(String inputPath) {
		HashMap<String, String> mp = new HashMap<String, String>();
		BufferedReader fileReader = null;
		String DELIMITER = null;
		try {

			if(delimiterCheck(inputPath).equals("tab")) DELIMITER ="\t";
			else DELIMITER = ",";
		} catch (Exception e1) {
			LOGGER.debug("DELIMITER not set Correctly"+e1);
			e1.printStackTrace();
		}
		try {
			String line = "";
			Resource input = ctx.getResource("file:" + inputPath);
			fileReader = new BufferedReader(new InputStreamReader(
					input.getInputStream()));

			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(DELIMITER);
				if (tokens.length >1) {
					String[] token2= tokens[2].split("///");
					LOGGER.debug(token2[0]);
					mp.put(tokens[0], token2[0]);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Couldn't read resource", e);
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				LOGGER.error("Couldn't close file reader", e);
				e.printStackTrace();
			}
		}
		return mp;
	}

	private static String delimiterCheck(String inputPath) {

		String line=null;
		int noOfLinesToScan=100;
		int count =0;
		String[]tabs = null ;
		String[] comma = null;
		int a[] = new int[noOfLinesToScan] ;
		int b[] = new int[noOfLinesToScan] ;

		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(inputPath));
			while ((line = fileReader.readLine()) != null && count<noOfLinesToScan)
			{
				tabs= line.split("\t");
				comma=line.split(",");

				a[count]=tabs.length;
				b[count]=comma.length;	
				count++;
			}
		} catch (IOException e) {
			LOGGER.debug("Exception Occured while reading file"+e);
			e.printStackTrace();
		}

		if(tabs.length>comma.length) return "tab";
		else return "comma";
	}



}
