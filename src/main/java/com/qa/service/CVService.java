package com.qa.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qa.constants.TraineeConstants;
import com.qa.domain.CV;
import com.qa.domain.Trainee;
import com.qa.rest.Endpoints;
import com.qa.webservices.IConsumer;
import com.qa.webservices.IProducer;

@Service
public class CVService implements ICVService {

	private final Logger logger = LoggerFactory.getLogger(Endpoints.class);

	@Autowired
	private JmsTemplate template;

	@Autowired
	private IProducer producer;

	@Autowired
	private IConsumer consumer;

	private Trainee trainee;

	public ResponseEntity<?> uploadFile(MultipartFile cvDoc, Long traineeID) {
		logger.debug("Multiple file upload! With UploadModel");
		CV cv = putFileIntoCVObject(cvDoc);
		//trainee = traineeWithID(traineeID);
		//trainee.setCvList(updateCVList(cv, traineeID));
		template.convertAndSend(TraineeConstants.QUEUE_NAME, trainee);
		return new ResponseEntity(TraineeConstants.SERVICE_RESPONSEENTITY_MESSAGE, HttpStatus.OK);
	}

	public CV putFileIntoCVObject(MultipartFile cvDoc) {
		CV cv = new CV();
		cv.setFiles(cvDoc);
		return cv;
	}

	public List<Trainee> getAllTrainees() {
		producer.askForTrainees();
		return (List<Trainee>) consumer.getListOfTrainees();

	}

	public Trainee findTraineeByID(Long ID) {
		return getAllTrainees().stream().filter(e -> e.getID().equals(ID)).findFirst().get();

	}

	public Trainee traineeWithID(Long traineeID) {
		trainee = new Trainee();
		trainee.inputFirstName(findTraineeByID(traineeID).getFirstName());
		trainee.inputLastName(findTraineeByID(traineeID).getLastName());
		trainee.inputID(traineeID);
		trainee.inputUserName(findTraineeByID(traineeID).getUserName());
		trainee.inputcurrentlyHired(findTraineeByID(traineeID).isCurrentlyHired());
		trainee.inputFlagged(findTraineeByID(traineeID).isFlagged());
		trainee.setCvList(findTraineeByID(traineeID).getCvList());
		return trainee;
	}

	public List<Optional<CV>> updateCVList(Optional<CV> cv, Long traineeID) {
		List<Optional<CV>> CVList = new ArrayList<Optional<CV>>();
		CVList = traineeWithID(traineeID).getCvList();
		CVList.add(cv);
		return CVList;

	}

	public Trainee createTrainee(Trainee trainee) {
		return trainee;
	}

	public List<Optional<CV>> getCV(Long traineeID) {
		trainee = traineeWithID(traineeID);
		return trainee.getCvList();
	}



}
