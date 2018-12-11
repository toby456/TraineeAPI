package com.qa.TraineeAPI.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qa.TraineeAPI.persistence.domain.CV;
import com.qa.TraineeAPI.persistence.domain.Trainee;
import com.qa.TraineeAPI.service.ICVService;
import com.qa.TraineeAPI.service.ITraineeService;

@RequestMapping("${URL.base}")
@CrossOrigin
@RestController
public class Endpoints {

	@Autowired
	private ICVService cvService;

	@Autowired
	private ITraineeService traineeServ;

	@PostMapping("${upload_endpoint}")
	public String multiUploadFileModel(@RequestParam("cvDoc") MultipartFile cvDoc,
			@PathVariable String traineeUserName) {
		return cvService.uploadFile(cvDoc, traineeUserName);
	}

	@GetMapping("${URL.method.CV.getByID}/{cvID}")
	public Optional<CV> getCV(@PathVariable("cvID") Long cvid) {
		return cvService.getCV(cvid);
	}

	@GetMapping("${URL.method.CV.getAll}")
	public List<CV> getAllCVs() {
		return cvService.getAllCVs();
	}

	@GetMapping("${URL.method.CV.getForUser}/{userName}")
	public List<CV> getCVsForUser(@PathVariable("userName") String traineeUsername) {
		return cvService.getCVsForTrainee(traineeUsername);
	}

	@DeleteMapping("${URL.method.CV.delete}/{cvID}")
	public String deleteCV(@PathVariable("cvID") Long cvid) {
		return cvService.deleteCV(cvid);
	}

	@PostMapping("${URL.method.Trainee.add}")
	public String createTrainee(@RequestBody Trainee trainee, @RequestBody String password) {
		return traineeServ.create(trainee, password);
	}

	@GetMapping("${URL.method.Trainee.getByUserName}/{userName}")
	public Optional<Trainee> getTrainee(@PathVariable("userName") String username) {
		return traineeServ.get(username);
	}

	@GetMapping("${URL.method.Trainee.getAll}")
	public Iterable<Trainee> getTrainee() {
		return traineeServ.getAll();
	}

	@PutMapping("${URL.method.updateTrainee}/{userName}")
	public String updateTrainer(@PathVariable("userName") String username, @RequestBody Trainee trainee) {
		return traineeServ.update(username, trainee);
	}

	@DeleteMapping("${URL.method.Trainee.delete}/{userName}")
	public String deleteCV(@PathVariable("userName") String userName) {
		return traineeServ.delete(userName);
	}
}
