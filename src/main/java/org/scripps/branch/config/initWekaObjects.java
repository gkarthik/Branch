package org.scripps.branch.config;

import java.io.InputStream;

import org.scripps.branch.entity.Weka;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
 
public class initWekaObjects implements ApplicationContextAware {
 
  private static ApplicationContext ctx;
  private static Weka weka;
 
  @Override
  public void setApplicationContext(ApplicationContext appContext) throws BeansException {
	  ctx = appContext;
	  Weka wekaObj = new Weka();
	  if(wekaObj.getTrain()==null){
			Resource train_file = ctx.getResource("/WEB-INF/data/Metabric_clinical_expression_DSS_sample_filtered.arff");
			try {
				wekaObj.buildWeka(train_file.getInputStream(), null, "metabric_with_clinical");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  weka = wekaObj;
	  System.out.println("Instances: "+weka.getTrain().numInstances());
  }
 
 
  public static ApplicationContext getApplicationContext() {
    return ctx;
  }
  
  public static Weka getWeka() {
	    return weka;
	  }
}
