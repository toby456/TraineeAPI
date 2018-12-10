package com.qa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qa.constants.TraineeConstants;
import com.qa.domain.CV;
import com.qa.domain.Trainee;

@Service
@Primary
public class CVServiceLocal implements ICVService {

	private List<Trainee> traineeList = new ArrayList<Trainee>();

	private Trainee trainee;

	private CV cv;

	public ResponseEntity<?> uploadFile(MultipartFile cvDoc, Long traineeID) {
		cv = putFileIntoCVObject(cvDoc);
		trainee = traineeWithID(traineeID);
		trainee.setCvList(updateCVList(Optional.of(cv), traineeID));
		traineeList.remove(findTraineeByID(traineeID));
		traineeList.add(trainee);
		return new ResponseEntity(TraineeConstants.SERVICE_RESPONSE_ENTITY_MESSAGE, HttpStatus.OK);
	}

	public CV putFileIntoCVObject(MultipartFile cvDoc) {
		cv = new CV();
		cv.setFiles(cvDoc);
		return cv;
	}

	public Trainee createTrainee(Trainee trainee) {
		traineeList.add(trainee);
		return trainee;
	}

	public List<Optional<CV>> updateCVList(Optional<CV> cv, Long traineeID) {
		List<Optional<CV>> CVList = new ArrayList<Optional<CV>>();
		if (traineeWithID(traineeID).getCvList() != null) {
			CVList.add(cv);
		}
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

	public List<Optional<CV>> getCV(Long traineeID) {
		return findTraineeByID(traineeID).getCvList();
	}

	@Override
	public List<Trainee> getAllTrainees() {
		return traineeList;
	}

}
