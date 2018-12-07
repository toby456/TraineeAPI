package com.qa.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.qa.domain.CV;
import com.qa.domain.Trainee;

public interface ICVService {

	ResponseEntity<?> uploadFile(MultipartFile cvDoc, Long traineeID);
	
	List<CV> getCV(Long traineeID);
	
	Trainee createTrainee(Trainee trainee);
	
	List<CV> updateCVList(CV cv, Long traineeID);
	
	Trainee traineeWithID(Long traineeID);
	
	Trainee findTraineeByID(Long traineeID);
	
	List<Trainee> getAllTrainees();
	
	CV putFileIntoCVObject(MultipartFile cvDoc);

}
