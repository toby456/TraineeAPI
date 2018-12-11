package com.qa.TraineeAPI.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.qa.TraineeAPI.persistence.domain.CV;
import com.qa.TraineeAPI.persistence.domain.CVRequest;
import com.qa.TraineeAPI.persistence.domain.Trainee;
import com.qa.TraineeAPI.persistence.domain.UserRequest;

@Component
public class Producer {

	@Autowired
	private JmsTemplate jmsTemplate;

	public String produce(Trainee trainee) {
		jmsTemplate.convertAndSend(Constants.INCOMING_TRAINEE_QUEUE_NAME, trainee);
		return Constants.TRAINEE_QUEUED_MESSAGE;
	}

	public String produce(Iterable<Trainee> trainees) {
		jmsTemplate.convertAndSend(Constants.INCOMING_TRAINEE_QUEUE_NAME, trainees);
		return Constants.TRAINEES_QUEUED_MESSAGE;
	}

	public String produce(UserRequest request) {
		jmsTemplate.convertAndSend(Constants.INCOMING_TRAINEE_QUEUE_NAME, request);
		return Constants.REQUEST_QUEUED_MESSAGE;

	}

	public String produce(CV cv) {
		jmsTemplate.convertAndSend(Constants.INCOMING_CV_QUEUE_NAME, cv);
		return Constants.TRAINEE_QUEUED_MESSAGE;
	}

	public String produce(CVRequest request) {
		jmsTemplate.convertAndSend(Constants.INCOMING_CV_QUEUE_NAME, request);
		return Constants.REQUEST_QUEUED_MESSAGE;

	}
}
