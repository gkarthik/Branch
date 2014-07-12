package org.scripps.branch.tests;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.scripps.branch.config.PersistenceJPAConfig;
import org.scripps.branch.entity.Feature;
import org.scripps.branch.entity.Pathway;
import org.scripps.branch.repository.FeatureRepository;
import org.scripps.branch.repository.PathwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class }, loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class PathwayTest {

	@Autowired
	@Qualifier("pathwayRepository")
	PathwayRepository path;

	@Test
	public void testSearchPathway() {
		List<Pathway> pList = path.searchPathways("Alanine, aspartate and glutamate metabolism - Homo sapiens (human)");
		for (Pathway f : pList) {
			if (f.getId()==1) {
				assertEquals(f.getName(), "Alanine, aspartate and glutamate metabolism - Homo sapiens (human)");
				assertEquals(f.getSource_db(), "KEGG");
			}
		}

	}
	
	
}
