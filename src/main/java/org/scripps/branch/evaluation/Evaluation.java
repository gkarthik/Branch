package org.scripps.branch.evaluation;

import java.util.LinkedHashMap;

import org.scripps.branch.classifier.ManualTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.NominalPrediction;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

public class Evaluation extends weka.classifiers.Evaluation{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Evaluation.class);

	public Evaluation(Instances data) throws Exception {
		super(data);
	}
		
	private FastVector m_Predictions;
	
	private int m_pred = -1;
	
	LinkedHashMap<String, Classifier> listOfFc = new LinkedHashMap<String, Classifier>();

	  /**
	   * Evaluates the classifier on a single instance and records the
	   * prediction (if the class is nominal).
	   *
	   * @param classifier machine learning classifier
	   * @param instance the test instance to be classified
	   * @return the prediction made by the classifier
	   * @throws Exception if model could not be evaluated 
	   * successfully or the data contains string attributes
	   */
		@Override
	  public double evaluateModelOnceAndRecordPrediction(Classifier classifier,
	      Instance instance) throws Exception {
			m_pred = -1;
			ManualTree t = ((ManualTree)classifier);
	    Instance classMissing = (Instance)instance.copy();
	    double pred = 0;
	    classMissing.setDataset(instance.dataset());
	    classMissing.setClassMissing();
	    if (m_ClassIsNominal) {
	      if (m_Predictions == null) {
		m_Predictions = new FastVector();
	      }
	      t.setParentNode(t);
	      double [] dist = classifier.distributionForInstance(classMissing);
	      pred = Utils.maxIndex(dist);
	      if(t.getM_pred()!=-1){
	    	  pred = t.getM_pred();
	      }
	      if (dist[(int)pred] <= 0) {
		pred = Instance.missingValue();
	      }
	      m_pred = (int) pred;
	      updateStatsForClassifier(dist, instance);
	      m_Predictions.addElement(new NominalPrediction(instance.classValue(), dist, 
		  instance.weight()));
	    } else {
	      pred = classifier.classifyInstance(classMissing);
	      updateStatsForPredictor(pred, instance);
	    }
	    return pred;
	  }
		
		 /**
		   * Updates all the statistics about a classifiers performance for 
		   * the current test instance.
		   *
		   * @param predictedDistribution the probabilities assigned to 
		   * each class
		   * @param instance the instance to be classified
		   * @throws Exception if the class of the instance is not
		   * set
		   */
		@Override
		  protected void updateStatsForClassifier(double [] predictedDistribution,
		      Instance instance)
		  throws Exception {

		    int actualClass = (int)instance.classValue();

		    if (!instance.classIsMissing()) {
		      updateMargins(predictedDistribution, actualClass, instance.weight());

		      // Determine the predicted class (doesn't detect multiple 
		      // classifications)
		      int predictedClass = -1;
		      double bestProb = 0.0;
		      bestProb = predictedDistribution[m_pred];
		      predictedClass = m_pred;
//		      for(int i = 0; i < m_NumClasses; i++) {
//			if (predictedDistribution[i] > bestProb) {
//			  predictedClass = i;
//			  bestProb = predictedDistribution[i];
//			}
//		      }

		      m_WithClass += instance.weight();

		      // Determine misclassification cost
		      if (m_CostMatrix != null) {
			if (predictedClass < 0) {
			  // For missing predictions, we assume the worst possible cost.
			  // This is pretty harsh.
			  // Perhaps we could take the negative of the cost of a correct
			  // prediction (-m_CostMatrix.getElement(actualClass,actualClass)),
			  // although often this will be zero
			  m_TotalCost += instance.weight()
			  * m_CostMatrix.getMaxCost(actualClass, instance);
			} else {
			  m_TotalCost += instance.weight() 
			  * m_CostMatrix.getElement(actualClass, predictedClass,
			      instance);
			}
		      }

		      // Update counts when no class was predicted
		      if (predictedClass < 0) {
			m_Unclassified += instance.weight();
			return;
		      }

		      double predictedProb = Math.max(MIN_SF_PROB,
			  predictedDistribution[actualClass]);
		      double priorProb = Math.max(MIN_SF_PROB,
			  m_ClassPriors[actualClass]
			                / m_ClassPriorsSum);
		      if (predictedProb >= priorProb) {
			m_SumKBInfo += (Utils.log2(predictedProb) - 
			    Utils.log2(priorProb))
			    * instance.weight();
		      } else {
			m_SumKBInfo -= (Utils.log2(1.0-predictedProb) - 
			    Utils.log2(1.0-priorProb))
			    * instance.weight();
		      }

		      m_SumSchemeEntropy -= Utils.log2(predictedProb) * instance.weight();
		      m_SumPriorEntropy -= Utils.log2(priorProb) * instance.weight();

		      updateNumericScores(predictedDistribution, 
			  makeDistribution(instance.classValue()), 
			  instance.weight());

		      // Update other stats
		      m_ConfusionMatrix[actualClass][predictedClass] += instance.weight();
		      if (predictedClass != actualClass) {
			m_Incorrect += instance.weight();
		      } else {
			m_Correct += instance.weight();
		      }
		    } else {
		      m_MissingClass += instance.weight();
		    }
		  }
	
}
