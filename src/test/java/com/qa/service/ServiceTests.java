package com.qa.service;

import java.awt.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import com.qa.TraineeAPI.persistence.domain.Trainee;
import com.qa.TraineeAPI.persistence.domain.User;
import com.qa.TraineeAPI.persistence.domain.UserRequest;
import com.qa.TraineeAPI.persistence.domain.UserRequest.requestType;
import com.qa.TraineeAPI.service.TraineeService;
import com.qa.TraineeAPI.util.Producer;
import com.qa.testConstants.TestConstants;



@RunWith(MockitoJUnitRunner.class)
public class ServiceTests {
	
	@InjectMocks
	private TraineeService service;
	
	@Mock
	private Producer producer;
	
	@Mock
	private JmsTemplate jmsTemplate;
	
	
	@Test
	public void testGet() {
	UserRequest thisRequest = new UserRequest();
	User dummyUser = new Trainee(TestConstants.USERNAME);
	Mockito.when(producer.produce(thisRequest)).thenReturn(TestConstants.REQUEST_QUEUED_MESSAGE); 
	Mockito.when(jmsTemplate.receiveAndConvert(TestConstants.OUTGOING_TRAINEE_QUEUE_NAME)).thenReturn(dummyUser);
	Assert.assertEquals(Optional.of(dummyUser), service.get(TestConstants.USERNAME));
	}
	
	@Test
	public void testGetAll() {
		User dummyUser = new Trainee(TestConstants.USERNAME);
		UserRequest thisRequest = new UserRequest();
		ArrayList traineeList = new ArrayList();
		traineeList.add(dummyUser);
		thisRequest.setHowToAct(requestType.READALL);
		Mockito.when(producer.produce(thisRequest)).thenReturn(TestConstants.REQUEST_QUEUED_MESSAGE); 
		Mockito.when(jmsTemplate.receiveAndConvert(TestConstants.OUTGOING_TRAINEE_QUEUE_NAME)).thenReturn(traineeList);
		Assert.assertEquals(traineeList, service.getAll());
	}
	
	@Test
	public void testDelete() {
		
	}
	
	
}
