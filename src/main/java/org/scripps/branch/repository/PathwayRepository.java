package org.scripps.branch.repository;


import java.util.List;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.scripps.branch.entity.Pathway;

@Transactional
public interface PathwayRepository extends JpaRepository<Pathway, Long> {

//	@Query("select f from Pathway p,Feature f where p.name=?1 and p.entrez_id =f.unique_id group by c.entrez_id")
//	List<Pathway> getGenesOfPathways(String pathwayName);

//	@Query("select p from Pathway p where p.name LIKE concat('%',concat(?1,'%')) group by p.name")
	@Query("select p from Pathway p where p.name=?1")
	List<Pathway> searchPathways(String name);
}
	