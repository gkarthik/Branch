package org.scripps.branch.tests;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.scripps.branch.config.ApplicationContextConfig;
import org.scripps.branch.entity.Feature;
import org.scripps.branch.entity.Tree;
import org.scripps.branch.entity.User;
import org.scripps.branch.repository.FeatureRepository;
import org.scripps.branch.repository.TreeRepository;
import org.scripps.branch.repository.UserRepository;
import org.scripps.branch.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationContextConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TreeTest {

	@Autowired
	private FeatureRepository f;

	@Autowired
	@Qualifier("treeRepository")
	private TreeRepository t;

	@Autowired
	@Qualifier("treeService")
	private TreeService ts;

	@Autowired
	private UserRepository u;

	@Test
	public void testRepo() throws SQLException {
		Tree newTree = t.findById(21);
		assertEquals(newTree == null, false);

		User user = u.findById(3);
		List<Tree> tList = t.findByUser(user);
		assertEquals(tList.size() == 0, false);

		tList = t.getByOtherUser(user);
		assertEquals(tList.size() == 0, false);

		tList = t.getAllTrees();
		assertEquals(tList.size() == 0, false);

		long count = t.getTotalCount();
		assertEquals(count != 0, true);

		List<Feature> fList = new ArrayList<Feature>();
		fList.add(f.getByDbId(1604));
		user = u.findById(3);
		assertEquals(user != null, true);
		count = t.getCountOfFeature(fList, user);
		System.out.println(count);
		assertEquals(count != 0, true);

		// List<Feature> fList = new ArrayList<Feature>();
		// fList.add(f.getByDbId(1604));
		// User user = u.findById(3);
		// double nov = ts.getUniqueIdNovelty(fList, user);
		// System.out.println(nov);
		// assertEquals(nov != 0, true);
	}

}
