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
import org.springframework.web.bind.annotation.RequestMapping;

import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.model.Student;

@RestController
@RequestMapping("/student")
public class StudentController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	
	@Autowired
	private StudentDao studentDao;
	
	@GetMapping(value = "/")
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
	
	@GetMapping(value="/fname={name}")
	public Map<String, Object> getStudentsByFName(@PathVariable String name){
		logger.info("Get sudents called");
		logger.info(name);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Student student: studentDao.findByFirstName(name)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return map;
	}
	
	@GetMapping(value="/lname={name}")
	public Map<String, Object> getStudentsByLName(@PathVariable String name){
		logger.info("Get sudents called");
		logger.info(name);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Student student: studentDao.findByLastName(name)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return map;
	}
	
	@GetMapping(value="/lname={lname}&fname={fname}")
	public Map<String, String> getStudentByFirstAndLastName(@PathVariable String lname, @PathVariable String fname){
		logger.warn(lname);
		logger.warn(fname);
		return studentDao.findByFirstNameAndLastName(fname, lname).toJson();
	}
	
	@DeleteMapping(value="/sid={sid}")
	public void deleteBySid(@PathVariable Long sid) {
		studentDao.deleteById(sid);
	}
	
	
}
