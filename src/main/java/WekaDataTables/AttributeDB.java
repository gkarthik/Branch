package WekaDataTables;

import javax.persistence.*;

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
@Table(name="attribute",  uniqueConstraints = {
		@UniqueConstraint(name = "dataset_index_name", columnNames = { "col_index","name","dataset" } )})


public class AttributeDB {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column(nullable=false,unique = true)
	private int col_index;

	@Column
	private String name=null;

	@Column
	private String dataset;

	@Column
	private float relieff;


	@ManyToOne
	@JoinColumn(name="feature_id",
	insertable=false, updatable=false,
	nullable=false)
	private FeatureDB featuredb;


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCol_index() {
		return col_index;
	}

	public void setCol_index(int col_index) {
		this.col_index = col_index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public float getRelieff() {
		return relieff;
	}

	public void setReliefF(float relieff) {
		this.relieff = relieff;
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


}
