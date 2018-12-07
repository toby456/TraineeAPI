package com.qa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.qa.constants.TraineeConstants;
import com.qa.domain.CV;
import com.qa.domain.Trainee;

public class CVServiceLocal implements ICVService {

	private List<Trainee> traineeList = new ArrayList();

	private Trainee trainee;

	public ResponseEntity<?> uploadFile(MultipartFile cvDoc, Long traineeID) {
		CV cv = putFileIntoCVObject(cvDoc);
		trainee = traineeWithID(traineeID);
		trainee.setCvList(updateCVList(cv, traineeID));
		traineeList.remove(findTraineeByID(traineeID));
		traineeList.add(trainee);
		return new ResponseEntity(TraineeConstants.SERVICE_RESPONSEENTITY_MESSAGE, HttpStatus.OK);
	}

	public CV putFileIntoCVObject(MultipartFile cvDoc) {
		CV cv = new CV();
		cv.setFiles(cvDoc);
		return cv;
	}

	public Trainee createTrainee(Trainee trainee) {
		traineeList.add(trainee);
		return trainee;
	}

	public List<CV> updateCVList(CV cv, Long traineeID) {
		List<CV> CVList = new ArrayList<CV>();
		CVList = traineeWithID(traineeID).getCvList();
		CVList.add(cv);
		return CVList;

	}

	public Trainee traineeWithID(Long traineeID) {
		Trainee trainee = new Trainee();
		trainee.inputFirstName(findTraineeByID(traineeID).getFirstName());
		trainee.inputLastName(findTraineeByID(traineeID).getLastName());
		trainee.inputID(traineeID);
		trainee.inputUserName(findTraineeByID(traineeID).getUserName());
		trainee.inputcurrentlyHired(findTraineeByID(traineeID).isCurrentlyHired());
		trainee.inputFlagged(findTraineeByID(traineeID).isFlagged());
		trainee.setCvList(findTraineeByID(traineeID).getCvList());
		return trainee;
	}

	public Trainee findTraineeByID(Long traineeID) {
		return traineeList.stream().filter(e -> e.getID().equals(traineeID)).findFirst().get();

	}

	public List<CV> getCV(Long traineeID) {
		return findTraineeByID(traineeID).getCvList();
	}

	@Override
	public List<Trainee> getAllTrainees() {
		return traineeList;
	}

}
