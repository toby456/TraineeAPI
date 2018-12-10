package com.qa.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qa.domain.CV;
import com.qa.domain.Trainee;
import com.qa.service.ICVService;

@RequestMapping("${base_endpoint}")
@CrossOrigin
@RestController
public class CVLocalEndpoints {
	
	@Autowired
	private ICVService service;

	
	@RequestMapping(value="${local_upload_endpoint}",method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(@RequestParam("file") MultipartFile cvDoc, @PathVariable("id") Long traineeID ) {
		return service.uploadFile(cvDoc, traineeID);
	}

	@PostMapping("${local_create_trainee_endpoint}")
	public Trainee createTrainee(@RequestBody Trainee trainee) {
		return service.createTrainee(trainee);
	}
	
	@GetMapping("${local_get_cv_endpoint}")
	public List<Optional<CV>> getCV(@PathVariable("traineeID") Long traineeID) {
		return service.getCV(traineeID);
	}
	

}
