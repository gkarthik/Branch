package org.scripps.branch.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feature")
	private List<Attribute> feature_id;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "features")
	private List<Tree> trees;

	@Column(name = "unique_id", length = 50, unique = true)
	private String unique_id;

	@Column(name = "short_name", length = 30)
	private String short_name;

	@Column(name = "long_name", length = 250)
	private String long_name;

	@Column(name = "description")
	private String description;

	@Basic(optional = false)
	@Column(name = "created", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private DateTime created;

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updated;

	public Feature() {

	}

	public Feature(long id, String unique_id, String short_name,
			String long_name, String description, DateTime created,
			DateTime updated) {
		super();
		this.id = id;
		this.unique_id = unique_id;
		this.short_name = short_name;
		this.long_name = long_name;
		this.description = description;
		this.created = created;
		this.updated = updated;
	}

	public void addAttribute(Attribute attribute) {
		this.feature_id.add(attribute);
		if (attribute.getFeature() != this) {
			attribute.setFeature(this);
		}
	}

	public DateTime getCreated() {
		return created;
	}

	public DateTime getCreationTime() {
		return created;
	}

	public String getDescription() {
		return description;
	}

	public List<Attribute> getFeature_id() {
		return feature_id;
	}

	public Long getId() {
		return id;
	}

	public String getLong_name() {
		return long_name;
	}

	public DateTime getModificationTime() {
		return updated;
	}

	public String getShort_name() {
		return short_name;
	}

	public String getUnique_id() {
		return unique_id;
	}

	public DateTime getUpdated() {
		return updated;
	}

	@PrePersist
	public void prePersist() {
		DateTime now = DateTime.now();
		this.created = now;
		this.updated = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.updated = DateTime.now();
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFeature_id(List<Attribute> feature_id) {
		this.feature_id = feature_id;
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

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("created", this.getCreationTime())
				.append("unique_id", this.getUnique_id())
				.append("updated", this.getModificationTime())
				.append("description", this.getDescription())
				.append("short_name", this.getShort_name())
				.append("long_name", this.getLong_name()).toString();
	}

}
