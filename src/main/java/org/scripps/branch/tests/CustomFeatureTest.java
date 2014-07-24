package org.scripps.branch.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.scripps.branch.config.ApplicationContext;
import org.scripps.branch.entity.CustomFeature;
import org.scripps.branch.entity.Weka;
import org.scripps.branch.globalentity.WekaObject;
import org.scripps.branch.repository.CustomFeatureRepository;
import org.scripps.branch.service.CustomFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("production")
@ContextConfiguration(classes = { ApplicationContext.class }, loader = AnnotationConfigWebContextLoader.class)
public class CustomFeatureTest {

	@Autowired
	private CustomFeatureService ser;

	@Autowired
	private CustomFeatureRepository cfRepo;

	@Autowired
	private WekaObject weka;

	@Test
	public void addCustomFeature() {
		Weka wekaobj = weka.getWeka();
		HashMap mp = ser.findOrCreateCustomFeature("Test", "@1960", "EGR3", -1,
				"metabric_with_clinical", wekaobj);
		assertEquals(mp == null, false);
	}

	@Test
	public void getTestCase() {
		Weka wekaobj = weka.getWeka();
		HashMap mp = ser.getTestCase("custom_feature_74", wekaobj);
		System.out.println(mp.size());
		assertEquals(mp == null, false);
	}

	@Test
	public void searchCustomFeature() {
		List<CustomFeature> cfList = cfRepo.searchCustomFeatures("test");
		System.out.println(cfList.size());
		assertEquals(cfList == null, false);
	}

}