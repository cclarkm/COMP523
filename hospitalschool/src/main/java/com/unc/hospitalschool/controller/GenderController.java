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
@RequestMapping("/gender")
public class GenderController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private GenderDao genderDao;
	
	@GetMapping(value = "/")
	@ResponseBody
	public Map<String, Object> getAllGenders() {
		logger.info("Get all genders called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Gender gender: genderDao.findAll()) {
			jsons.add(gender.toJson());
		}
		map.put("genders", jsons);
		return map;	
	}

	@PostMapping(value="/new") //also need to pass in whatever is being changed; could be multiple things
	public Gender newGender(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return genderDao.save(new Gender(body.get("gender")));

	}
	
	
}
