package com.qa.domain;


import org.springframework.web.multipart.MultipartFile;

public class CV {
	
	private Long cvID;
	private MultipartFile files;
	
	
	public CV(Long cvID, MultipartFile files) {
		this.cvID= cvID;
		this.files= files;
		
	}

	public CV() {
		
	}

	public Long getCvID() {
		return cvID;
	}

	public void setCvID(Long cvID) {
		this.cvID = cvID;
	
	}

	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile cvDoc) {
		this.files = cvDoc;
	}
	
	
	

}
