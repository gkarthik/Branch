package org.scripps.branch.entity.forms;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadForm {
	private String filename;
	private CommonsMultipartFile fileData;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public String getFilename() {
		return filename;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}