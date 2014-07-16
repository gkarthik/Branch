package org.scripps.branch.service;

import java.util.List;

import org.scripps.branch.entity.Feature;
import org.scripps.branch.entity.User;
import org.scripps.branch.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeServiceImpl implements TreeService{
	
	@Autowired
	TreeRepository treeRepo;
	
	@Override
	public double getUniqueIdNovelty(List<Feature> fList, User user){
		long n = treeRepo.getCountOfFeature(fList, user);
		long base = treeRepo.getTotalCount();
		double nov = 0;
			if(base>0 && n > 0){
				nov = (1 - Math.log(n)/Math.log(base));
			}else if(base == 0 || n == 0){//With this condition, novelty = Infinity error resolved.
				nov = 1; //First time card used.
			}
		return nov;
	}
}
