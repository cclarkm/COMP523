package com.unc.hospitalschool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unc.hospitalschool.dao.*;
import com.unc.hospitalschool.model.*;

@RestController
@RequestMapping("/gender")
public class GenderController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private GenderDao genderDao;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllGenders() {
		logger.info("Get all genders called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Gender gender: genderDao.findAll()) {
			jsons.add(gender.toJson());
		}
		map.put("genders", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping
	public ResponseEntity<Object> newGender(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		if (!body.containsKey("gender")) {
			return ResponseEntity.badRequest().body("gender field not provided");
		}
		genderDao.save(new Gender(body.get("gender")));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Object> updateGender(@RequestBody Map<String, String> body, @PathVariable int id) {
		Gender gender = genderDao.findById(id);
		logger.info("Updating gender " + id);
		if (gender == null) {
			logger.error("Unable to update - gender with id: " + id + " not found");
			return ResponseEntity.badRequest().body("Unable to update - gender with id: " + id + " not found");
		}
		if (body.containsKey("gender")) {
			gender.setGender(body.get("gender"));
			genderDao.save(gender);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Unable to update - gender; incorrect request data");
			return ResponseEntity.badRequest().body("Unable to update - gender. Incorrect request field");
		}
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Object> deleteByGid(@PathVariable int id) {
		Gender gender = genderDao.findById(id);
		if (gender == null) {
			logger.error("Unable to delete - gender with id: " + id + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - gender with id: " + id + " not found");
		} else {
			genderDao.delete(gender);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
