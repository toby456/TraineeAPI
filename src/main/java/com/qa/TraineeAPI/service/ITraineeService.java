package com.qa.TraineeAPI.service;

import java.util.Optional;

import com.qa.TraineeAPI.persistence.domain.Trainee;

public interface ITraineeService {

	String create(Trainee trainee, String password);

	Optional<Trainee> get(String username);

	Iterable<Trainee> getAll();

	String update(String username, Trainee updatedDetails);

	String delete(String username);

}
