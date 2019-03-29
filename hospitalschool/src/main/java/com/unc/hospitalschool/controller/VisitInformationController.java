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
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllCounties() {
		logger.info("Get all visit infos called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visitInformation: visitInformationDao.findAll()) {
			jsons.add(visitInformation.toJson());
		}
		map.put("visitInfos", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping
	public ResponseEntity<Object> newVisitInformation(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		Student student = studentDao.findBySid(Integer.parseInt(body.get("sid")));
		String dov = body.get("dov");
		String notes = body.get("notes");
		Teacher teacher = teacherDao.findByTid(Integer.parseInt(body.get("tid")));
		LogType logType = logTypeDao.findByLid(Integer.parseInt(body.get("logType")));
		boolean clinic = Boolean.parseBoolean(body.get("clinic"));
		visitInformationDao.save(new VisitInformation(student, dov, notes, teacher, logType, clinic));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/{sid}")
	public ResponseEntity<Map<String, Object>> getLogsByStudent(@PathVariable int sid) {
		logger.info("Get Logs By Student Called with ID:");
		logger.info(Integer.toString(sid));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visit: visitInformationDao.findByStudentOrderByDovDesc(studentDao.findBySid(sid))) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	@GetMapping(value="/{sid}/tid={tid}")
	public ResponseEntity<Map<String, Object>> getLogsByStudentAndTeacher(@PathVariable int sid, @PathVariable int tid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Student student = studentDao.findBySid(sid);
		Teacher teacher = teacherDao.findByTid(tid);
		
		for (VisitInformation visit: visitInformationDao.findByStudentAndTeacherOrderByDovDesc(student, teacher)) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	@GetMapping(value="/{sid}/lid={lid}")
	public ResponseEntity<Map<String, Object>> getLogsByStudentAndType(@PathVariable int sid, @PathVariable int lid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Student student = studentDao.findBySid(sid);
		LogType logType = logTypeDao.findByLid(lid);
		
		for (VisitInformation visit: visitInformationDao.findByStudentAndLogTypeOrderByDovDesc(student, logType)) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	@GetMapping(value="/{sid}/tid={tid}/lid={lid}")
	public ResponseEntity<Map<String, Object>> getLogsByStudentAndTeacherAndType(@PathVariable int sid, @PathVariable int tid, @PathVariable int lid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Student student = studentDao.findBySid(sid);
		Teacher teacher = teacherDao.findByTid(tid);
		LogType logType = logTypeDao.findByLid(lid);
		
		
		for (VisitInformation visit: visitInformationDao.findByStudentAndTeacherAndLogTypeOrderByDovDesc(student, teacher, logType)) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Object> updateVisitInformation(@RequestBody Map<String, String> body, @PathVariable int id) {
		VisitInformation visitInformation = visitInformationDao.findById(id);
		logger.info("Updating visitInformation " + id);
		if (visitInformation == null) {
			logger.error("Unable to update - visitInformation with id: " + id + " not found");
			return ResponseEntity.badRequest().body("Unable to update visitInformation with id: " + id + " not found");
		}
		for (String x: body.keySet()) {
			switch(x) {
			case "student":
				visitInformation.setStudent(studentDao.findBySid(Integer.parseInt(body.get("student"))));
				break;
			case "dov":
				visitInformation.setDov(body.get("dov"));
				break;
			case "notes":
				visitInformation.setNotes(body.get("notes"));
				break;
			case "teacher":
				visitInformation.setTeacher(teacherDao.findByTid(Integer.parseInt(body.get("teacher"))));
				break;
			case "logType":
				visitInformation.setLogType(logTypeDao.findByLid(Integer.parseInt(body.get("logType"))));
				break;
			case "clinic":
				visitInformation.setClinic(Boolean.parseBoolean(body.get("clinic")));
				break;
			default:
				logger.error("Cannot update " + x + " because the field does not exist");
			}
		}
		visitInformationDao.save(visitInformation);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable int id) {
		VisitInformation visitInformation = visitInformationDao.findById(id);
		if (visitInformation == null) {
			logger.error("Unable to delete - visitInformation with id: " + id + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - visitInformation with id: " + id + " not found");
		} else {
			visitInformationDao.delete(visitInformation);
			return new ResponseEntity<>(HttpStatus.OK);	
		}
	}
}
