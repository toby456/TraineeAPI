package com.qa.TraineeAPI.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.qa.TraineeAPI.util.Producer;
import com.qa.TraineeAPI.persistence.domain.Trainee;
import com.qa.TraineeAPI.persistence.domain.User;
import com.qa.TraineeAPI.persistence.domain.UserRequest;
import com.qa.TraineeAPI.persistence.domain.UserRequest.requestType;
import com.qa.TraineeAPI.util.Constants;

public class TraineeService implements ITraineeService {

	@Autowired
	private Producer producer;

	@Autowired
	private JmsTemplate jmsTemplate;

	public String create(Trainee trainee, String password) {
		producer.produce(trainee);
		return Constants.TRAINEE_QUEUED_MESSAGE;
	}

	public Optional<Trainee> get(String userName) {
		UserRequest thisRequest = new UserRequest();
		User dummyUser = new Trainee(userName);
		thisRequest.setHowToAct(requestType.READ);
		thisRequest.setUserToAddOrUpdate(dummyUser);
		producer.produce(thisRequest);
		return (Optional<Trainee>) jmsTemplate.receiveAndConvert(Constants.OUTGOING_TRAINEE_QUEUE_NAME);
	}

	public Iterable<Trainee> getAll() {
		UserRequest thisRequest = new UserRequest();
		thisRequest.setHowToAct(requestType.READALL);
		producer.produce(thisRequest);
		return (Iterable<Trainee>) jmsTemplate.receiveAndConvert(Constants.OUTGOING_TRAINEE_QUEUE_NAME);
	}

	public String delete(String userName) {
		UserRequest thisRequest = new UserRequest();
		User dummyUser = new Trainee(userName);
		thisRequest.setHowToAct(requestType.DELETE);
		thisRequest.setUserToAddOrUpdate(dummyUser);
		producer.produce(thisRequest);
		return Constants.REQUEST_QUEUED_MESSAGE;

	}

	public String update(String userName, Trainee updatedTrainee) {
		UserRequest thisRequest = new UserRequest();
		User dummyUser = updatedTrainee;
		dummyUser.setUsername(userName);
		thisRequest.setHowToAct(requestType.UPDATE);
		thisRequest.setUserToAddOrUpdate(dummyUser);
		producer.produce(thisRequest);
		return Constants.REQUEST_QUEUED_MESSAGE;
	}

}
