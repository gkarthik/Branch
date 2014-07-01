package org.scripps.branch.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//DROP TABLE IF EXISTS `feature`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//CREATE TABLE `feature` (
//  `id` int(10) NOT NULL AUTO_INCREMENT,
//  `unique_id` varchar(50) NOT NULL,
//  `short_name` varchar(30) DEFAULT NULL,
//  `long_name` varchar(250) DEFAULT NULL,
//  `description` text,
//  `created` date DEFAULT NULL,
//  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//  PRIMARY KEY (`id`)
//) ENGINE=MyISAM AUTO_INCREMENT=43191 DEFAULT CHARSET=latin1;

@Entity
@Table(name = "feature")
public class Feature {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

//	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "feature_id",fetch = FetchType.LAZY)
//	//@JoinColumn(name="feature_id")
//	private Collection<AttributeDB> attributes;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
	private Collection<Attribute> attributes;

	@ManyToMany(mappedBy = "featuredb")
	private List<Tree> treedb;

	@Column(name = "unique_id", length = 50, unique = true)
	private String unique_id;

	@Column(name = "short_name", length = 30)
	private String short_name;

	@Column(name = "long_name", length = 250)
	private String long_name;

	@Column(name = "description")
	private String description;

	// @PrePersist
	// public void prePersist() {
	// Date now = DateTime.now();
	// this.created = now;
	// this.updated = now;
	// }
	//
	// @PreUpdate
	// public void preUpdate() {
	// this.updated = DateTime.now();
	// }

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getLong_name() {
		return long_name;
	}

	public String getShort_name() {
		return short_name;
	}

	public String getUnique_id() {
		return unique_id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}
	
	public void getByUnique_id(String unique_id) {
		
	}


	public void addAttributes(Attribute attribute) {

		if (!attributes.contains(attribute)) {

			attributes.add(attribute);
		}
	}

	public Collection<Attribute> getAttributes() {

		return attributes;

	}



}
