package org.scripps.branch.repository;

import javax.transaction.Transactional;

import org.scripps.branch.entity.CustomSet;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface CustomSetRepository extends JpaRepository<CustomSet, Long> {

	CustomSet findById(long id);

}