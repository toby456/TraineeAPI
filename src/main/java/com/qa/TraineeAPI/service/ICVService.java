package com.qa.TraineeAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.qa.TraineeAPI.persistence.domain.CV;
import com.qa.TraineeAPI.persistence.domain.Trainee;

public interface ICVService {
	
	String uploadFile(MultipartFile cvDoc, String traineeUserName);

	String uploadFile(CV cvDoc, String traineeUserName);

	Optional<CV> getCV(Long cvID);

	List<CV> getAllCVs();

	List<CV> getCVsForTrainee(String traineeUsername);

	List<CV> getCVsForTrainee(Trainee trainee);

	String deleteCV(Long cvID);

}
