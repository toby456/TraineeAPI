package com.qa.webservices;

import java.util.List;

import com.qa.domain.Trainee;

public interface IConsumer {
	
	List<Trainee> recieveTrainees(Iterable<Trainee> CVs);
	
	List<Trainee> getListOfTrainees();


}
