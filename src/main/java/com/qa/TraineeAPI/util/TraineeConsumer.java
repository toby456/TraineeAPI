package com.qa.TraineeAPI.util;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.qa.TraineeAPI.persistence.domain.Trainee;

@Component
public class TraineeConsumer {

	@JmsListener(destination = Constants.OUTGOING_TRAINEE_QUEUE_NAME, containerFactory = "myFactory")
	public Trainee traineeReceiveMessage(Trainee trainee) {
		return trainee;
	}

	@JmsListener(destination = Constants.OUTGOING_TRAINEE_QUEUE_NAME, containerFactory = "myFactory")
	public Iterable<Trainee> traineeReceiveMessage(Iterable<Trainee> trainees) {
		return trainees;
	}

}