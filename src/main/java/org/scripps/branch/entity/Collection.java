package org.scripps.branch.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "collection")
public class Collection {

	@Basic(optional = false)
	@Column(name = "created", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private DateTime created;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "collection")
	@JsonBackReference
	private List<Dataset> datasets;

	@Column(length = 1000)
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column
	private String name;

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updated;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = true, updatable = true)
	private User user;

	public DateTime getCreated() {
		return created;
	}

	public List<Dataset> getDatasets() {
		return datasets;
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public User getUser() {
		return user;
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

	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
