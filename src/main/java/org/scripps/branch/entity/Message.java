package org.scripps.branch.entity;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -4093981756240899937L;
	private String description;
	private String filename;
	private String owner;

	public Message() {
		super();
	}

	public Message(String owner, String description, String filename) {
		super();
		this.owner = owner;
		this.description = description;
		this.filename = filename;
	}

	public String getDescription() {
		return description;
	}

	public String getFilename() {
		return filename;
	}

	public String getOwner() {
		return owner;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Message [owner=" + owner + ", description=" + description
				+ ", filename=" + filename + "]";
	}

}