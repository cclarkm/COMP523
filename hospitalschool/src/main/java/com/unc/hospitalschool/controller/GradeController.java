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
@RequestMapping("/grade")
public class GradeController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private GradeDao gradeDao;
	
	@GetMapping(value = "/")
	@ResponseBody
	public Map<String, Object> getAllCounties() {
		logger.info("Get all counties called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Grade grade: gradeDao.findAll()) {
			jsons.add(grade.toJson());
		}
		map.put("grades", jsons);
		return map;	
	}

	@PostMapping(value="/new") //also need to pass in whatever is being changed; could be multiple things
	public Grade newStudent(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return gradeDao.save(new Grade(body.get("grade")));

	}
	
	
}
