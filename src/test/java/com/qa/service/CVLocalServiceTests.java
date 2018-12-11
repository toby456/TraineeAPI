package com.qa.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.TraineeAPI.persistence.domain.Trainee;
import com.qa.TraineeAPI.service.CVServiceLocal;




@RunWith(MockitoJUnitRunner.class)
public class CVLocalServiceTests {
	
	@InjectMocks
	private CVServiceLocal service;


	@Test
	public void testGetAllTrainees() {
		List<Trainee> listTrainees = new ArrayList<Trainee>();
		Trainee trainee = new Trainee();
		listTrainees.add(trainee);
		Mockito.when(service.getAllTrainees()).thenReturn(listTrainees);
		Assert.assertEquals(listTrainees, service.getAllTrainees());
		
	}
	
	
	
	

}
