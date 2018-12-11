package com.qa.rest;

import java.util.List;
import java.util.Optional;

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
import com.qa.constants.TraineeConstants;

@RequestMapping(TraineeConstants.BASE_ENDPOINT)
@CrossOrigin
@RestController
public class Endpoints {

	@Autowired
	private ICVService service;
	
	@Autowired
	private CVService services;

	@PostMapping(TraineeConstants.UPLOAD_ENDPOINT)
	public String uploadFile(@RequestParam(TraineeConstants.CV_DOC) MultipartFile cvDoc, @PathVariable Long traineeID) {
		return service.uploadFile(cvDoc, traineeID);

	}

	@PostMapping(TraineeConstants.CREATE_TRAINEE_ENDPOINT)
	public Trainee createTrainee(@RequestBody Trainee trainee) {
		return services.createTrainee(trainee);
	}
	
	@GetMapping(TraineeConstants.GET_CV_ENDPOINT)
	public List<Optional<CV>> getCV(@PathVariable(TraineeConstants.TRAINEE_ID) Long traineeID) {
		return services.getCV(traineeID);
	}
	
}
