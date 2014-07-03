package org.scripps.branch.repository;

import java.util.List;

import org.scripps.branch.entity.Attribute;
import org.springframework.data.repository.Repository;

public interface AttributeRepository extends Repository<Attribute, Long> {
	  List<Attribute> findByFeatureUniqueId(String unique_id, String dataset);
}
