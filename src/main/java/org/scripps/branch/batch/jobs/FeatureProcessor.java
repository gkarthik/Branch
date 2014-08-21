package org.scripps.branch.batch.jobs;

import org.scripps.branch.entity.Feature;
import org.scripps.branch.repository.FeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class FeatureProcessor implements ItemProcessor<Feature, Feature> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FeatureProcessor.class);

	@Override
	public Feature process(Feature f) {
		return f;
	}
}
