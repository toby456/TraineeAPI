package com.qa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.web.MockMultipartFile;

import com.qa.constants.TraineeConstants;
import com.qa.domain.CV;
import com.qa.domain.Trainee;
import com.qa.testConstants.Constants;
import com.qa.webservices.Consumer;
import com.qa.webservices.Producer;

@RunWith(MockitoJUnitRunner.class)
public class CVServiceTests {

	@InjectMocks
	private CVService service;

	@Mock
	private Producer producer;

	@Mock
	private Consumer consumer;
	
	@Mock
	private JmsTemplate template;

	@Test
	public void testGetAllTrainees() {
		List<Trainee> listTrainees = new ArrayList<Trainee>();
		Trainee trainee = new Trainee();
		trainee.setFirstName(Constants.TRAINEE_NAME);
		listTrainees.add(trainee);
		Mockito.when(producer.askForTrainees()).thenReturn(Constants.QUEUE_MESSAGE);
		Mockito.when(consumer.getListOfTrainees()).thenReturn(listTrainees);
		Assert.assertEquals(listTrainees, service.getAllTrainees());

	}

	@Test
	public void testCreateTrainee() {
		Trainee trainee = new Trainee();
		Assert.assertEquals(trainee, service.createTrainee(trainee));
	}

	@Test
	public void testFindTraineeByID() {
		Trainee trainee = new Trainee();
		List<Trainee> traineeList= new ArrayList<Trainee>();
		trainee.setFirstName("Toby");
		trainee.setLastName("Toby");
		trainee.setCurrentlyHired(true);
		trainee.setFlagged(true);
		trainee.setUserName("Toby");
		trainee.setID(1L);
		trainee.setCvList(null);
		traineeList.add(trainee);
		Mockito.when(consumer.getListOfTrainees()).thenReturn(traineeList);
		Assert.assertEquals(trainee, service.findTraineeByID(1L));
		
		
	}
	
	@Test 
	public void testPutFileIntoCVObject() {
		MockMultipartFile mockMultipartFile = new MockMultipartFile(Constants.MOCK_TRAINEE_FILE,Constants.MOCK_FILENAME,
	              Constants.FILE_TYPE, Constants.MOCK_TEST_DATA.getBytes());
		CV cv = new CV();
		cv.setFiles(mockMultipartFile);
		Assert.assertEquals(cv, service.putFileIntoCVObject(mockMultipartFile));
		
	}
	
	@Test
	public void testUpdateCVList() {
		CV cv = new CV();
		Trainee trainee = new Trainee();
		List<Trainee> traineeList= new ArrayList<Trainee>();
		trainee.setID(1L);
		trainee.setCvList(null);
		traineeList.add(trainee);
		List<Optional<CV>> cvList = new ArrayList<Optional<CV>>();
		cvList.add(Optional.of(cv));
		Mockito.when(consumer.getListOfTrainees()).thenReturn(traineeList);
		Assert.assertEquals(cvList, service.updateCVList(Optional.of(cv), trainee.getID()));
	}
	
	@Test
	public void testTraineeWithID() {
		Trainee trainee = new Trainee();
		List<Trainee> traineeList= new ArrayList<Trainee>();
		trainee.setFirstName("Toby");
		trainee.setLastName("Toby");
		trainee.setCurrentlyHired(true);
		trainee.setFlagged(true);
		trainee.setUserName("Toby");
		trainee.setID(1L);
		trainee.setCvList(null);
		traineeList.add(trainee);
		Mockito.when(consumer.getListOfTrainees()).thenReturn(traineeList);
		Assert.assertEquals(trainee.getFirstName(), service.traineeWithID(1L).getFirstName());
		Assert.assertEquals(trainee.getLastName(), service.traineeWithID(1L).getLastName());
		Assert.assertEquals(trainee.getUserName(), service.traineeWithID(1L).getUserName());
		Assert.assertEquals(trainee.isCurrentlyHired(), service.traineeWithID(1L).isCurrentlyHired());
		Assert.assertEquals(trainee.isFlagged(), service.traineeWithID(1L).isFlagged());
		Assert.assertEquals(trainee.getID(), service.traineeWithID(1L).getID());	
		
	}
	
	@Test
	public void testUploadFile() {
		CV cv = new CV();
		List<Optional<CV>> cvList = new ArrayList<Optional<CV>>();
		cvList.add(Optional.of(cv));
		Trainee trainee = new Trainee();
		List<Trainee> traineeList= new ArrayList<Trainee>();
		trainee.setID(1L);
		traineeList.add(trainee);
		Mockito.when(consumer.getListOfTrainees()).thenReturn(traineeList);
		MockMultipartFile mockMultipartFile = new MockMultipartFile(Constants.MOCK_TRAINEE_FILE,Constants.MOCK_FILENAME,
	              Constants.FILE_TYPE, Constants.MOCK_TEST_DATA.getBytes());
		cv.setFiles(mockMultipartFile);
		//Mockito.when(service.putFileIntoCVObject(mockMultipartFile)).thenReturn(cv);
		//Mockito.when(service.traineeWithID(trainee.getID())).thenReturn(trainee);
		Mockito.when(service.updateCVList(Optional.of(cv), trainee.getID())).thenReturn(cvList);
		Assert.assertEquals(Constants.SERVICE_RESPONSE_ENTITY_MESSAGE, HttpStatus.OK, service.uploadFile(mockMultipartFile, 1L));
		
		
		
	}
	
	@Test
	public void testGenerateUniqueID() {
		List<Trainee> listTrainees = new ArrayList<Trainee>();
		Mockito.when(consumer.getListOfTrainees()).thenReturn(listTrainees);
		Trainee mockTrainee = new Trainee();
		Trainee otherTrainee = new Trainee();
		mockTrainee.setUserName("user");
		otherTrainee.setUserName("user2");	
		mockTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(1), mockTrainee.getID());
		listTrainees.add(mockTrainee);
		otherTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(2), otherTrainee.getID());
	}
	
	@Test
	public void testGenerateUniqueIDWithDeleteLast() {
		List<Trainee> listTrainees = new ArrayList<Trainee>();
		Mockito.when(consumer.getListOfTrainees()).thenReturn(listTrainees);
		Trainee mockTrainee = new Trainee();
		Trainee otherTrainee = new Trainee();
		Trainee nextTrainee = new Trainee();
		mockTrainee.setUserName("user");
		otherTrainee.setUserName("user2");
		nextTrainee.setUserName("user3");
		mockTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(1), mockTrainee.getID());
		listTrainees.add(mockTrainee);
		otherTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(2), otherTrainee.getID());
		listTrainees.remove(otherTrainee);
		nextTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(2), nextTrainee.getID());
	}
	
	@Test
	public void testGenerateUniqueIDWithDeleteFirst() {
		List<Trainee> listTrainees = new ArrayList<Trainee>();
		Mockito.when(consumer.getListOfTrainees()).thenReturn(listTrainees);
		Trainee mockTrainee = new Trainee();
		Trainee otherTrainee = new Trainee();
		Trainee nextTrainee = new Trainee();
		mockTrainee.setUserName("user");
		otherTrainee.setUserName("user2");
		nextTrainee.setUserName("user3");
		mockTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(1), mockTrainee.getID());
		listTrainees.add(mockTrainee);
		otherTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(2), otherTrainee.getID());
		listTrainees.add(otherTrainee);
		listTrainees.remove(mockTrainee);
		nextTrainee.setID(service.generateUniqueID());
		Assert.assertEquals(Long.valueOf(3), nextTrainee.getID());
	}
	
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	

}
