package com.qa.TraineeAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qa.TraineeAPI.persistence.domain.CV;
import com.qa.TraineeAPI.persistence.domain.CVRequest;
import com.qa.TraineeAPI.persistence.domain.Trainee;
import com.qa.TraineeAPI.persistence.domain.UserRequest;
import com.qa.TraineeAPI.persistence.domain.CVRequest.requestType;
import com.qa.TraineeAPI.util.Constants;
import com.qa.TraineeAPI.util.Producer;

@Service
public class CVService implements ICVService {

	@Autowired
	private Producer producer;

	@Autowired
	private JmsTemplate jmsTemplate;

	public String uploadFile(CV cv, String traineeUserName) {
		Trainee author = new Trainee(traineeUserName);
		author.setCvs(new ArrayList<CV>());
		author.getCvs().add(cv);
		UserRequest request = new UserRequest();
		request.setUserToAddOrUpdate(author);
		request.setHowToAct(com.qa.TraineeAPI.persistence.domain.UserRequest.requestType.ADDCV);
		producer.produce(request);
		return Constants.CV_QUEUED_MESSAGE;
	}

	public String uploadFile(MultipartFile cvDoc, String traineeUserName) {
		CV thisCV = new CV();
		thisCV.setFiles(cvDoc);
		return uploadFile(thisCV, traineeUserName);
	}

	public List<CV> getCVsForTrainee(Trainee trainee) {
		UserRequest request = new UserRequest();
		request.setUserToAddOrUpdate(trainee);
		request.setHowToAct(com.qa.TraineeAPI.persistence.domain.UserRequest.requestType.READALL);
		producer.produce(request);
		return (List<CV>) jmsTemplate.receiveAndConvert(Constants.OUTGOING_CV_QUEUE_NAME);
	}

	public List<CV> getCVsForTrainee(String traineeUserName) {
		Trainee thisTrainee = new Trainee(traineeUserName);
		return getCVsForTrainee(thisTrainee);
	}

	public String deleteCV(Long cvID) {
		CVRequest request = new CVRequest();
		request.setcvIDtoActUpon(cvID);
		request.setType(requestType.DELETE);
		producer.produce(request);
		return Constants.CV_QUEUED_MESSAGE;
	}

	@Override
	public Optional<CV> getCV(Long cvID) {
		CVRequest request = new CVRequest();
		request.setcvIDtoActUpon(cvID);
		request.setType(requestType.READ);
		producer.produce(request);
		return (Optional<CV>) jmsTemplate.receiveAndConvert(Constants.OUTGOING_CV_QUEUE_NAME);
	}

	@Override
	public List<CV> getAllCVs() {
		CVRequest request = new CVRequest();
		request.setType(requestType.READALL);
		producer.produce(request);
		return (List<CV>) jmsTemplate.receiveAndConvert(Constants.OUTGOING_CV_QUEUE_NAME);
	}

}
