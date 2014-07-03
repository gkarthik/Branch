package org.scripps.branch.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.scripps.branch.entity.Attribute;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

@Transactional
public interface AttributeRepository extends JpaRepository<Attribute, Long>{
	List<Attribute> findByFeatureUniqueId(String unique_id, String dataset);
}
