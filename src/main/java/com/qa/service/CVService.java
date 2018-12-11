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
	
	private CV cv;

	public String uploadFile(MultipartFile cvDoc, Long traineeID) {
		logger.debug(TraineeConstants.LOGGER_DEBUG_MESSAGE);
		cv = putFileIntoCVObject(cvDoc);
		trainee = traineeWithID(traineeID);
		trainee.setCvList(updateCVList(Optional.of(cv), traineeID));
		template.convertAndSend(TraineeConstants.QUEUE_NAME, trainee);
		return TraineeConstants.SERVICE_FILE_UPLOAD_MESSAGE;
	}

	public CV putFileIntoCVObject(MultipartFile cvDoc) {
		CV cv = new CV();
		cv.setFiles(cvDoc);
		return cv;
	}

	public List<Trainee> getAllTrainees() {
		producer.askForTrainees();
		List<Trainee> listTrainee=consumer.getListOfTrainees();
		return listTrainee;

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
		if (traineeWithID(traineeID).getCvList() != null) {
			CVList.add(cv);
		}
		CVList.add(cv);
		return CVList; 
	}

	public Trainee createTrainee(Trainee trainee) {
		trainee.setID(generateUniqueID());
		trainee.setRole(TraineeConstants.SET_TRAINEE_ROLE);
		return trainee; 
	}

	public List<Optional<CV>> getCV(Long traineeID) {
		trainee = traineeWithID(traineeID);
		return trainee.getCvList();
	}
	
	public Long generateUniqueID() {
		Long ID;
		if(getAllTrainees().isEmpty()) {
			return ID=1L;
		} else {
			ID=getAllTrainees().stream().mapToLong(e->e.getID()).max().getAsLong()+1L;
			return ID;
		}
		
		
	}



}
