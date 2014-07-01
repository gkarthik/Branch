package WekaDataTables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

//CREATE TABLE `attribute` (
//		  `id` int(10) NOT NULL AUTO_INCREMENT,
//		  `col_index` int(11) NOT NULL,
//		  `name` varchar(30) DEFAULT NULL,
//		  `dataset` varchar(50) DEFAULT NULL,
//		  `reliefF` float DEFAULT NULL,
//		  `created` date DEFAULT NULL,
//		  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//		  `feature_id` int(11) DEFAULT NULL,
//		  PRIMARY KEY (`id`),
//		  UNIQUE KEY `dataset_index_name` (`col_index`,`name`,`dataset`)
//		) ENGINE=MyISAM AUTO_INCREMENT=15521 DEFAULT CHARSET=latin1;

//name="unique_id", length = 50, nullable = false, unique = true

@Entity
@Table(name = "attribute", uniqueConstraints = { @UniqueConstraint(name = "dataset_index_name", columnNames = {
		"col_index", "name", "dataset" }) })
public class AttributeDB {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column(unique = true)
	private int col_index;

	@Column
	private String name;

	@Column
	private String dataset;

	@Column
	private float relieff;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "feature_id", insertable = false, updatable = false, nullable = false)
//	private FeatureDB feature_id;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 private FeatureDB feature;


//	private Long feature_id;
//	
//	public long getFeature_id() {
//		return feature_id;
//	}
//
//	public void setFeature_id(long feature_id) {
//		this.feature_id = featuredb.getId();
//	}

	public FeatureDB getFeature_id() {
		return feature;
	}

	public void setFeature(FeatureDB result) {
		this.feature = result;
	}

	@Column(name = "created", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime created = null;

	@Column(name = "updated", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updated;

	public int getCol_index() {
		return col_index;
	}

	public DateTime getCreated() {
		return created;
	}

	public String getDataset() {
		return dataset;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getRelieff() {
		return relieff;
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

	public void setCol_index(int col_index) {
		this.col_index = col_index;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReliefF(float relieff) {
		this.relieff = relieff;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

}
