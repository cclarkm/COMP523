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
@RequestMapping("/grade")
public class GradeController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private GradeDao gradeDao;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllGrades() {
		logger.info("Get all grades called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Grade grade: gradeDao.findAll()) {
			jsons.add(grade.toJson());
		}
		map.put("grades", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping
	public ResponseEntity<Object> newGrade(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		if (!body.containsKey("grade")) {
			return ResponseEntity.badRequest().body("grade field not provided");
		}
		gradeDao.save(new Grade(body.get("grade")));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{gid}")
	public ResponseEntity<Object> updateGrade(@RequestBody Map<String, String> body, @PathVariable int gid) {
		Grade grade = gradeDao.findByGid(gid);
		logger.info("Updating grade " + gid);
		if (grade == null) {
			logger.error("Unable to update - grade with gid: " + gid + " not found");
			return ResponseEntity.badRequest().body("Unable to update - grade with gid: " + gid + " not found");
		}
		if (body.containsKey("grade")) {
			grade.setGrade(body.get("grade"));
			gradeDao.save(grade);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Unable to update - county; incorrect request data");
			return ResponseEntity.badRequest().body("Unable to update - grade. Incorrect request field");
		}
	}
	
	@DeleteMapping(value="/{gid}")
	public ResponseEntity<Object> deleteByGid(@PathVariable int gid) {
		Grade grade = gradeDao.findByGid(gid);
		if (grade == null) {
			logger.error("Unable to delete - grade with gid: " + gid + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - grade with gid: " + gid + " not found");
		} else {
			gradeDao.delete(grade);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
