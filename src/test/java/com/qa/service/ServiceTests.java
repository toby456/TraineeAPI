package com.qa.service;

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
	Assert.assertEquals(dummyUser, service.get(TestConstants.USERNAME));
	}
}
