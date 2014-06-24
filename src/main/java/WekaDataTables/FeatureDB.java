package WekaDataTables;

import java.util.List;


import javax.persistence.*;

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
@Table(name="feature")

public class FeatureDB {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "featuredb")
	
//	@JoinColumn(name="feature_id")
	private List<AttributeDB> attributes;

	 @ManyToMany(mappedBy="featuredb")
	 private List<TreeDB> treedb;
	
	@Column(name="unique_id", length = 50, nullable = false, unique = true)
	private String unique_id;

	@Column(name = "short_name",length = 30)
	private String short_name=null;

	@Column(name="long_name",length=250)
	private String long_name=null;

	@Column(name="description")
	private String description;

	@Column(name="created",nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime created=null;

	@Column(name="updated", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updated;


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



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUnique_id() {
		return unique_id;
	}
	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public String getLong_name() {
		return long_name;
	}
	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DateTime getCreated() {
		return created;
	}
	public void setCreated(DateTime created) {
		this.created = created;
	}
	public DateTime getUpdated() {
		return updated;
	}
	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}


}
