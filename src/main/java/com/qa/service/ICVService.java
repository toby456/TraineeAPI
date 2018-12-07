package com.qa.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.qa.domain.CV;

public interface ICVService {

	ResponseEntity<?> multiUploadFileModel(MultipartFile file, Long ID);
	
	List<CV> getCV(Long traineeID);

}
