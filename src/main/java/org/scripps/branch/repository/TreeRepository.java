package org.scripps.branch.repository;

import org.scripps.branch.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
	Tree findById(int Id);
}