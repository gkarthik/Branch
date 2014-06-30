package WekaDataTables;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feature_id",insertable=false,updatable=false)
	private FeatureDB feature;

	public FeatureDB getFeature() {
		return feature;
	}

	public void setFeature(FeatureDB feature) {
		this.feature = feature;
		//		if(!feature.getFeature_id().contains(this)){
		//			feature.getFeature_id().add(this);
		//		}
	}



	public int getCol_index() {
		return col_index;
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




	public void setCol_index(int col_index) {
		this.col_index = col_index;
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


	public void setRelieff(float relieff) {
		this.relieff = relieff;
	}

	@Basic(optional = false)
	@Column(name = "created", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private DateTime created;

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

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updated;

	public DateTime getCreationTime() {
		return created;
	}



	public DateTime getModificationTime() {
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


	public AttributeDB(){

	}

	public AttributeDB(int id, int col_index, String name, String dataset,
			float relieff, DateTime created, DateTime updated) {
		super();
		this.id = id;
		this.col_index = col_index;
		this.name = name;
		this.dataset = dataset;
		this.relieff = relieff;
		this.created = created;
		this.updated = updated;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)

				.append("col_Index", this.getCol_index())
				.append("created", this.getCreated())
				.append("dataset", this.getDataset())
				.append("name", this.getName())
				.append("relieff", this.getRelieff())
				.append("updated", this.getUpdated())
				.append("feature_id",this.getFeature().getId()).toString();
	}




}

//@ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn(name = "feature_id", insertable = false, updatable = false, nullable = false)
//private FeatureDB feature_id;


//private Long feature_id;
//
//public long getFeature_id() {
//	return feature_id;
//}
//
//public void setFeature_id(long feature_id) {
//	this.feature_id = featuredb.getId();
//}
