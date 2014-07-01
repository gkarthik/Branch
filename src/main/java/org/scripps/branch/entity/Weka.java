package org.scripps.branch.entity;

import java.io.InputStream;
import java.util.Map;
import java.util.Random;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Weka {

	private Instances train = null;
	Instances test = null;
	Random rand;
	String eval_method; // {cross_validation, test_set, training_set}
	Map<String, Feature> features;
	String dataset;

	public Instances getTrain() {
		return train;
	}

	public void setTrain(Instances train) {
		this.train = train;
	}

	public Instances getTest() {
		return test;
	}

	public void setTest(Instances test) {
		this.test = test;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public String getEval_method() {
		return eval_method;
	}

	public void setEval_method(String eval_method) {
		this.eval_method = eval_method;
	}

	public Map<String, Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Map<String, Feature> features) {
		this.features = features;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public void buildWeka(InputStream train_stream, InputStream test_stream,
			String dataset) throws Exception {
		buildWeka(train_stream, test_stream, dataset, true);
	}

	public void buildWeka(InputStream train_stream, InputStream test_stream,
			String dataset, boolean setFeatures) throws Exception {
		setDataset(dataset);
		// get the data
		DataSource source = new DataSource(train_stream);
		setTrain(source.getDataSet());
		if (getTrain().classIndex() == -1) {
			getTrain().setClassIndex(getTrain().numAttributes() - 1);
		}
		train_stream.close();
		if (test_stream != null) {
			source = new DataSource(test_stream);
			test = source.getDataSet();
			if (test.classIndex() == -1) {
				test.setClassIndex(test.numAttributes() - 1);
			}
			test_stream.close();
		}
		rand = new Random(1);
		// specify how hands evaluated {cross_validation, test_set,
		// training_set}
		eval_method = "training_set"; // "cross_validation";//
		// assumes that feature table has already been loaded
		// get the features related to this weka dataset
		if (setFeatures) {
			setFeatures(Feature.getByDataset(dataset, false));
		}
	}

}
