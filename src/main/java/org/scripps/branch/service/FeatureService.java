package org.scripps.branch.service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.scripps.branch.entity.Dataset;
import org.scripps.branch.entity.Feature;
import org.springframework.stereotype.Service;

import weka.core.Instances;

@Service
public interface FeatureService {
	public List<Feature> rankFeatures(Instances data, Dataset d);
}
