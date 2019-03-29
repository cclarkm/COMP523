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
@RequestMapping("/teacher")
public class TeacherController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private TeacherDao teacherDao;
	
	@GetMapping
	@ResponseBody
	public Map<String, Object> getAllTeachers() {
		logger.info("Get all teachers called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Teacher Teacher: teacherDao.findAll()) {
			jsons.add(Teacher.toJson());
		}
		map.put("teachers", jsons);
		return map;	
	}

	@PostMapping
	public Teacher newTeacher(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return teacherDao.save(new Teacher(body.get("lName"), body.get("fName")));
	}
	
	@PutMapping(value="/{tid}")
	public Teacher updateTeacher(@RequestBody Map<String, String> body, @PathVariable int tid) {
		Teacher teacher = teacherDao.findByTid(tid);
		logger.info("Updating teacher " + tid);
		if (teacher == null) {
			logger.error("Unable to update - teacher with tid: " + tid + " not found");
			return null;
		}
		if (!body.containsKey("firstName") && !body.containsKey("lastName")) {
			logger.error("Unable to update - county; incorrect request data");
			return null;
		}
		if (body.containsKey("firstName")) {
			teacher.setFirstName(body.get("firstName"));
		}
		if (body.containsKey("lastName")) {
			teacher.setLastName(body.get("lastName"));
		}
		return teacherDao.save(teacher);
	}
	
	@DeleteMapping(value="/{tid}")
	public void deleteByTid(@PathVariable int tid) {
		Teacher teacher = teacherDao.findByTid(tid);
		if (teacher == null) {
			logger.error("Unable to delete - teacher with tid: " + tid + " not found");
		} else {
			teacherDao.delete(teacher);
		}
	}
}
