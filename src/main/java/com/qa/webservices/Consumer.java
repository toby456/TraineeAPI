package com.qa.webservices;

import java.util.List;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.qa.constants.TraineeConstants;
import com.qa.domain.Trainee;

@Component
@CrossOrigin
public class Consumer implements IConsumer {

	private List<Trainee> listOfTrainees;

	@JmsListener(destination = TraineeConstants.QUEUE_NAME, containerFactory = TraineeConstants.CONTAINER_FACTORY)
	public List<Trainee> recieveTrainees(Iterable<Trainee> trainees) {
		for (Trainee trainee : trainees) {
			listOfTrainees.add(trainee);
		}
		return listOfTrainees;
	}
	
	public List<Trainee> getListOfTrainees() {
		return listOfTrainees;
	}

}
