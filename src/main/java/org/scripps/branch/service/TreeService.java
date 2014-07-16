package org.scripps.branch.service;

import java.util.List;

import org.scripps.branch.entity.Feature;
import org.scripps.branch.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface TreeService{
	public double getUniqueIdNovelty(List<Feature> fList, User user);
}
