package com.qa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qa.domain.CV;
import com.qa.domain.Trainee;
import com.qa.service.CVService;
import com.qa.service.ICVService;

@RequestMapping("${base_endpoint}")
@CrossOrigin
@RestController
public class Endpoints {

	@Autowired
	private ICVService service;
	
	@Autowired
	private CVService services;

	@PostMapping("${upload_endpoint}")
	public ResponseEntity<?> multiUploadFileModel(@RequestParam("file") MultipartFile file, @PathVariable Long ID) {
		return service.multiUploadFileModel(file, ID);

	}

	@PostMapping("${create_trainee_endpoint}")
	public Trainee createTrainee(@RequestBody Trainee trainee) {
		return services.createTrainee(trainee);
	}
	
	@GetMapping("${get_cv_endpoint}")
	public List<CV> getCV(@PathVariable("traineeID") Long traineeID) {
		return services.getCV(traineeID);
	}
	
}
