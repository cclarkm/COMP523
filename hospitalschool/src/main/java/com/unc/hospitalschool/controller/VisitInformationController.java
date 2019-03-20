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
@RequestMapping("/visitInformation")
public class VisitInformationController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private VisitInformationDao visitInformationDao;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private LogTypeDao logTypeDao;
	
	@GetMapping(value = "/")
	@ResponseBody
	public Map<String, Object> getAllCounties() {
		logger.info("Get all visit infos called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visitInformation: visitInformationDao.findAll()) {
			jsons.add(visitInformation.toJson());
		}
		map.put("visitInfos", jsons);
		return map;	
	}

	@PostMapping(value="/new") //also need to pass in whatever is being changed; could be multiple things
	public VisitInformation newStudent(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		Student student = studentDao.findBySid(Integer.parseInt(body.get("sid")));
		String dov = body.get("dov");
		String notes = body.get("notes");
		Teacher teacher = teacherDao.findByTid(Integer.parseInt(body.get("tid")));
		LogType logType = logTypeDao.findByLid(Integer.parseInt(body.get("logType")));
		boolean clinic = Boolean.parseBoolean(body.get("clinic"));
		return visitInformationDao.save(new VisitInformation(student, dov, notes, teacher, logType, clinic));
	}
	
	@GetMapping(value="/sid={sid}")
	public Map<String, Object> getLogsByStudent(@PathVariable int sid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visit: visitInformationDao.findByStudentOrderByDovDesc(sid)) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return map;
	}
	
	@GetMapping(value="/sid={sid}?tid={tid}")
	public Map<String, Object> getLogsByStudentAndTeacher(@PathVariable int sid, @PathVariable int tid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visit: visitInformationDao.findByStudentAndTeacherOrderByDovDesc(sid, tid)) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return map;
	}
	
	@GetMapping(value="/sid={sid}?lid={lid}")
	public Map<String, Object> getLogsByStudentAndType(@PathVariable int sid, @PathVariable int lid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visit: visitInformationDao.findByStudentAndLogTypeOrderByDovDesc(sid, lid)) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return map;
	}
	
	@GetMapping(value="/sid={sid}?tid={tid}?lid={lid}")
	public Map<String, Object> getLogsByStudentAndTeacherAndType(@PathVariable int sid, @PathVariable int tid, @PathVariable int lid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visit: visitInformationDao.findByStudentAndTeacherAndLogTypeOrderByDovDesc(sid, tid, lid)) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return map;
	}
	
	
	
}
