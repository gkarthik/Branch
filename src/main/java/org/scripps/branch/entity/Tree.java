package org.scripps.branch.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tree")
public class Tree {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", insertable = true, updatable = true)
	@JsonManagedReference
	private User user;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "tree_feature", joinColumns = { @JoinColumn(name = "tree_id") }, inverseJoinColumns = { @JoinColumn(name = "feature_id") })
	private List<Feature> features;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "tree_customfeature", joinColumns = { @JoinColumn(name = "tree_id") }, inverseJoinColumns = { @JoinColumn(name = "custom_feature_id") })
	private List<CustomFeature> customFeatures;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "tree_customclassifier", joinColumns = { @JoinColumn(name = "tree_id") }, inverseJoinColumns = { @JoinColumn(name = "custom_classifier_id") })
	private List<CustomClassifier> customClassifiers;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "tree_customtreeclassifier", joinColumns = { @JoinColumn(name = "tree_id") }, inverseJoinColumns = { @JoinColumn(name = "custom_tree_id") })
	private List<Tree> customTreeClassifiers;

	@Column
	private String comment;

	@Column(name = "created", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime created = null;

	@Column
	private boolean user_saved;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prev_tree_id", insertable = true, updatable = true)
	private Tree prevTree;

	@OneToOne(fetch = FetchType.EAGER)
	@JsonManagedReference
	@JoinColumn(name = "score", insertable = true, updatable = true)
	private Score score;

	@Transient int rank = 0;
	
	@Column
	private boolean private_tree = false;

	@Column
	private String json_tree;

	public String getComment() {
		return comment;
	}
	
	@Transient
	public int getRank() {
		return rank;
	}

	public DateTime getCreated() {
		return created;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public long getId() {
		return id;
	}

	public String getJson_tree() {
		return json_tree;
	}

	public Tree getPrev_tree_id() {
		return prevTree;
	}

	public Score getScore() {
		return score;
	}

	public User getUser() {
		return user;
	}

	public boolean isPrivate_tree() {
		return private_tree;
	}

	public boolean isUser_saved() {
		return user_saved;
	}

	@PrePersist
	public void prePersist() {
		DateTime now = DateTime.now();
		this.created = now;

	}

	@PreUpdate
	public void preUpdate() {

	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setJson_tree(String json_tree) {
		this.json_tree = json_tree;
	}

	public void setPrev_tree_id(Tree newPrevTree) {
		this.prevTree = newPrevTree;
	}

	public void setPrivate_tree(boolean private_tree) {
		this.private_tree = private_tree;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUser_saved(boolean user_saved) {
		this.user_saved = user_saved;
	}
	
	public List<CustomFeature> getCustomFeatures() {
		return customFeatures;
	}

	public void setCustomFeatures(List<CustomFeature> customFeatures) {
		this.customFeatures = customFeatures;
	}

	public List<CustomClassifier> getCustomClassifiers() {
		return customClassifiers;
	}

	public void setCustomClassifiers(List<CustomClassifier> customClassifiers) {
		this.customClassifiers = customClassifiers;
	}

	public List<Tree> getCustomTreeClassifiers() {
		return customTreeClassifiers;
	}

	public void setCustomTreeClassifiers(List<Tree> customTreeClassifiers) {
		this.customTreeClassifiers = customTreeClassifiers;
	}
	
	@Transient
	public void setRank(int rank){
		this.rank = rank;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)

		.append("user_id", this.getUser())
				.append("feature", this.getFeatures())
				.append("comment", this.getComment())
				.append("user_saved", this.isUser_saved())
				.append("prev_tree_id", this.getPrev_tree_id())
				.append("isPrivate_tree", this.isPrivate_tree())
				.append("json_tree", this.getJson_tree()).toString();
	}

}
