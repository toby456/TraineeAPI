package com.qa.domain;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;



public class Trainee implements Serializable{
	
	private Long ID;
	private String userName;
	private String firstName;
	private String lastName;
	private boolean currentlyHired;
	private boolean flagged;
	private List<Optional<CV>>cvList;
	
	public Trainee() {
		
	}
	
	
	public Trainee inputID(Long ID) {
		this.ID=ID;
		return this;
	}
	
	public Trainee inputUserName(String userName) {
		this.userName=userName;
		return this;
	}
	
	public Trainee inputFirstName(String firstName) {
		this.firstName=firstName;
		return this;
	}
	
	public Trainee inputLastName(String lastName) {
		this.lastName=lastName;
		return this;
	}
	
	public Trainee inputcurrentlyHired(boolean currentlyHired) {
		this.currentlyHired=currentlyHired;
		return this;
	}
	
	public Trainee inputFlagged(boolean flagged) {
		this.flagged=flagged;
		return this;
	}
	
	public Trainee inputCVs(List<Optional<CV>> cvList) {
		this.cvList=cvList;
		return this;
	}
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isCurrentlyHired() {
		return currentlyHired;
	}

	public void setCurrentlyHired(boolean currentlyHired) {
		this.currentlyHired = currentlyHired;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Long getID() {
		return ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	public List<Optional<CV>> getCvList() {
		return cvList;
	}

	public void setCvList(List<Optional<CV>> cvList) {
		this.cvList = cvList;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	
	
	
	

}
