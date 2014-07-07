package org.scripps.branch.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.scripps.branch.entity.Attribute;
import org.scripps.branch.entity.Feature;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

@Transactional
public interface FeatureRepository extends JpaRepository<Attribute, Long>{
	Map<String, Feature> findByDataset(String dataset, boolean load_annotations_very_slowly);
}
