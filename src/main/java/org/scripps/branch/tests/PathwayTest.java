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
	PathwayRepository path;

	@Test
	public void testSearchPathway() {
		List<?> pList = path.searchPathways("Apoptosis");
		System.out.println(pList.size());
		assertEquals(pList!=null,true);
	}
	
	
}
