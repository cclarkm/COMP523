package com.unc.hospitalschool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.model.Student;

@RestController
public class StudentController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	
	@Autowired
	private StudentDao studentDao;
	
	@GetMapping(value = "/student")
	@ResponseBody
	public Map<String, Object> getAllStudents() {
		logger.info("Get all sudents called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Student student: studentDao.findAll()) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return map;
		
	}
}
