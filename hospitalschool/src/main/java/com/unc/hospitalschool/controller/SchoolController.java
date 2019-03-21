package com.unc.hospitalschool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/school")
public class SchoolController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private SchoolDao schoolDao;
	
	@GetMapping(value = "/")
	@ResponseBody
	public Map<String, Object> getAllSchools() {
		logger.info("Get all schools called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (School school: schoolDao.findAll()) {
			jsons.add(school.toJson());
		}
		map.put("schools", jsons);
		return map;	
	}

	@PostMapping(value="/new") //also need to pass in whatever is being changed; could be multiple things
	public School newSchool(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return schoolDao.save(new School(body.get("school")));
	}
	
	@PutMapping(value="/update/sid={sid}")
	public School updateSchool(@RequestBody Map<String, String> body, @PathVariable int sid) {
		School school = schoolDao.findBySid(sid);
		logger.info("Updating school " + sid);
		if (school == null) {
			logger.error("Unable to update - school with sid: " + sid + " not found");
			return null;
		}
		if (body.containsKey("school")) {
			school.setSchool(body.get("school"));
			return schoolDao.save(school);
		} else {
			logger.error("Unable to update - school; incorrect request data");
			return null;
		}
	}
	
}
