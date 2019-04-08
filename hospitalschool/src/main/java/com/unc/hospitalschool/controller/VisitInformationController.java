package com.unc.hospitalschool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
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
	
	
	private QPageRequest goToPage(int page, int size) {
		return new QPageRequest(page, size);
	}
	
	private boolean validatePageAndSize(int page, int size) {
		return (page >= 0) && (size >= 1);
	}
	
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllLogs() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (VisitInformation visitInformation: visitInformationDao.findAll()) {
			jsons.add(visitInformation.toJson());
		}
		map.put("visitInfos", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	
	
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT START
	 * ---------------------------------------------------------------------
	 */
	@GetMapping(value="/{sid}")
	public ResponseEntity<Object> getLogsByStudent(@PathVariable int sid) {
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
	
	@GetMapping(value="/{sid}/page={page}/size={size}")
	public ResponseEntity<Object> getLogsByStudent(@PathVariable int sid, @PathVariable int page, @PathVariable int size){
		if (!validatePageAndSize(page, size)) {
			return ResponseEntity.badRequest().body("Page must be >= 0 and Size must be > 0");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Page<VisitInformation> temp = visitInformationDao.findByStudentOrderByDovDesc(studentDao.findBySid(sid), goToPage(page, size));
		for (VisitInformation visit: temp.getContent()) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT END
	 * ---------------------------------------------------------------------
	 */
	
	
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT & TEACHER START
	 * ---------------------------------------------------------------------
	 */
	@GetMapping(value="/{sid}/tid={tid}")
	public ResponseEntity<Object> getLogsByStudentAndTeacher(@PathVariable int sid, @PathVariable int tid) {
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
	
	@GetMapping(value="/{sid}/tid={tid}/page={page}/size={size}")
	public ResponseEntity<Object> getLogsByStudentAndTeacher(@PathVariable int sid, @PathVariable int tid, @PathVariable int page, @PathVariable int size) {
		if (!validatePageAndSize(page, size)) {
			return ResponseEntity.badRequest().body("Page must be >= 0 and Size must be > 0");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Student student = studentDao.findBySid(sid);
		Teacher teacher = teacherDao.findByTid(tid);
		
		Page<VisitInformation> temp = visitInformationDao.findByStudentAndTeacherOrderByDovDesc(student, teacher, goToPage(page, size));
		for (VisitInformation visit: temp.getContent()) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT & TEACHER END
	 * ---------------------------------------------------------------------
	 */
	
	
	
	
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT & TYPE START
	 * ---------------------------------------------------------------------
	 */
	@GetMapping(value="/{sid}/lid={lid}")
	public ResponseEntity<Object> getLogsByStudentAndType(@PathVariable int sid, @PathVariable int lid) {
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
	
	@GetMapping(value="/{sid}/lid={lid}/page={page}/size={size}")
	public ResponseEntity<Object> getLogsByStudentAndType(@PathVariable int sid, @PathVariable int lid, @PathVariable int page, @PathVariable int size) {
		if (!validatePageAndSize(page, size)) {
			return ResponseEntity.badRequest().body("Page must be >= 0 and Size must be > 0");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Student student = studentDao.findBySid(sid);
		LogType logType = logTypeDao.findByLid(lid);
		
		Page<VisitInformation> temp = visitInformationDao.findByStudentAndLogTypeOrderByDovDesc(student, logType, goToPage(page, size));
		for (VisitInformation visit: temp.getContent()) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}	
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT & TYPE END
	 * ---------------------------------------------------------------------
	 */
	
	
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT & TEACHER & TYPE START
	 * ---------------------------------------------------------------------
	 */
	@GetMapping(value="/{sid}/tid={tid}/lid={lid}")
	public ResponseEntity<Object> getLogsByStudentAndTeacherAndType(@PathVariable int sid, @PathVariable int tid, @PathVariable int lid) {
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
	
	@GetMapping(value="/{sid}/tid={tid}/lid={lid}/page={page}/size={size}")
	public ResponseEntity<Object> getLogsByStudentAndTeacherAndType(@PathVariable int sid, @PathVariable int tid, @PathVariable int lid, @PathVariable int page, @PathVariable int size) {
		if (!validatePageAndSize(page, size)) {
			return ResponseEntity.badRequest().body("Page must be >= 0 and Size must be > 0");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Student student = studentDao.findBySid(sid);
		Teacher teacher = teacherDao.findByTid(tid);
		LogType logType = logTypeDao.findByLid(lid);
		
		Page<VisitInformation> temp = visitInformationDao.findByStudentAndTeacherAndLogTypeOrderByDovDesc(student, teacher, logType, goToPage(page, size));
		for (VisitInformation visit: temp.getContent()) {
			jsons.add(visit.toJson());
		}
		map.put("visits", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	/*
	 * ---------------------------------------------------------------------
	 * GET LOGS BY STUDENT & TEACHER & TYPE END
	 * ---------------------------------------------------------------------
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping
	public ResponseEntity<Object> newVisitInformation(@RequestBody Map<String, String> body) {
		VisitInformation visitInformation = new VisitInformation();
		if (!VisitInformation.validateFields(body)) {
			return ResponseEntity.badRequest().body("Not all required VisitInformation parameters provided");
		}
		try {
			this.setVisitInformationFields(body, visitInformation);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		visitInformationDao.save(visitInformation);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Object> updateVisitInformation(@RequestBody Map<String, String> body, @PathVariable int id) throws Exception {
		VisitInformation visitInformation = visitInformationDao.findById(id);
		logger.info("Updating visitInformation " + id);
		if (visitInformation == null) {
			logger.error("Unable to update - visitInformation with id: " + id + " not found");
			return ResponseEntity.badRequest().body("Unable to update visitInformation with id: " + id + " not found");
		}
		try {
			this.setVisitInformationFields(body, visitInformation);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
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

	private void setVisitInformationFields(Map<String, String> body, VisitInformation visitInformation) throws Exception {
		for (String x : body.keySet()) {
			switch (x) {
			case "sid":
				visitInformation.setStudent(studentDao.findBySid(parseInt("sid", (body.get("sid")))));
				break;
			case "dov":
				visitInformation.setDov(body.get("dov"));
				break;
			case "notes":
				visitInformation.setNotes(body.get("notes"));
				break;
			case "tid":
				visitInformation.setTeacher(teacherDao.findByTid(parseInt("tid", body.get("tid"))));
				break;
			case "logType":
				visitInformation.setLogType(logTypeDao.findByLid(parseInt("logType", body.get("logType"))));
				break;
			case "clinic":
				visitInformation.setClinic(parseBoolean("clinic", body.get("clinic")));
				break;
			default:
				logger.error("Cannot update " + x + " because the field does not exist");
			}
		}

	}
	
	private boolean parseBoolean(String fieldName, String fieldValue) throws Exception {
		if (fieldValue.equals("true") || fieldValue.equals("false")) {
			return Boolean.parseBoolean(fieldValue);
		} else {
			throw new Exception("Field " + fieldName + " must be boolean");
		}
	}

	private int parseInt(String fieldName, String fieldValue) throws Exception {
		try {
			return Integer.parseInt(fieldValue);
		} catch (Exception e) {
			throw new Exception("Field " + fieldName + " must be int");
		}
	}
}
