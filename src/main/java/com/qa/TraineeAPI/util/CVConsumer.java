package com.qa.TraineeAPI.util;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.qa.TraineeAPI.persistence.domain.CV;

@Component
public class CVConsumer {

	@JmsListener(destination = Constants.OUTGOING_CV_QUEUE_NAME, containerFactory = "myFactory")
	public CV cvReceiveMessage(CV cv) {
		return cv;
	}

	@JmsListener(destination = Constants.OUTGOING_CV_QUEUE_NAME, containerFactory = "myFactory")
	public Iterable<CV> trainerReceiveMessage(Iterable<CV> cvs) {
		return cvs;
	}

}