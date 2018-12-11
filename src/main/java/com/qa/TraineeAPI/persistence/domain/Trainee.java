package com.qa.TraineeAPI.persistence.domain;

import java.util.List;

import org.springframework.data.annotation.

@TypeAlias("Trainee")
public class Trainee extends User {

	private List<CV> cvs;

	public Trainee(String traineeUserName) {
		this.setUsername(traineeUserName);
	}

	public List<CV> getCvs() {
		return cvs;
	}

	public void setCvs(List<CV> cvs) {
		this.cvs = cvs;
	}

}
