package com.unc.hospitalschool.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unc.hospitalschool.dao.AdmissionDao;
import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.model.Admission;
import com.unc.hospitalschool.model.FieldValidation;
import com.unc.hospitalschool.model.Student;


@RestController
@RequestMapping("/admission")
public class AdmissionsController {

	
	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	
	
	@Autowired
	private AdmissionDao admissionDao;
	
	@Autowired
	private StudentDao studentDao;
	
	private QPageRequest goToPage(int page, int size) {
		return new QPageRequest(page, size);
	}
	
	private boolean validatePageAndSize(int page, int size) {
		return (page >= 0) && (size >= 1);
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllAdmissions() {
		logger.info("Get all admissions called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Admission admission: admissionDao.findAll()) {
			jsons.add(admission.toJson());
		}
		map.put("admissions", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	
	
	/*
	 * ---------------------------------------------------------------------
	 * GET ADMISSIONS BY STUDENT START
	 * ---------------------------------------------------------------------
	 */
	@GetMapping(value="/{sid}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAdmissionsByStudent(@PathVariable int sid) {
		logger.info("Get all admissions by student called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Admission admission: admissionDao.findByStudent(studentDao.findBySid(sid))) {
			jsons.add(admission.toJson());
		}
		map.put("admissions", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}
	
	@GetMapping(value="/{sid}/page={page}/size={size}")
	public ResponseEntity<Object> getAdmissionsByStudent(@PathVariable int sid, @PathVariable int page, @PathVariable int size){
		if (!validatePageAndSize(page, size)) {
			return ResponseEntity.badRequest().body("Page must be >= 0 and Size must be > 0");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		Page<Admission> temp = admissionDao.findByStudent(studentDao.findBySid(sid), goToPage(page, size));
		for (Admission admission: temp.getContent()) {
			jsons.add(admission.toJson());
		}
		map.put("admissions", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	/*
	 * ---------------------------------------------------------------------
	 * GET ADMISSIONS BY STUDENT END
	 * ---------------------------------------------------------------------
	 */
	
	
	
	
	
	
	@PostMapping
	public ResponseEntity<Object> newAdmission(@RequestBody Map<String, String> body) {
		if (!Admission.validateFields(body)) {
			return ResponseEntity.badRequest().body("Not all required Admission parameters provided");
		}
		Admission admit = new Admission();
		
		try {
			setAdmissionFields(body, admit);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		admissionDao.save(admit);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{aid}")
	public ResponseEntity<Object> updateAdmission(@RequestBody Map<String, String> body, @PathVariable int aid) {
		Admission admit = admissionDao.findByAid(aid);
		if (admit == null) {
			logger.error("Unable to update - Admission with aid: " + aid + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			setAdmissionFields(body, admit);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
		admissionDao.save(admit);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@DeleteMapping(value="/{aid}")
	public ResponseEntity<Object> deleteAdmission(@PathVariable int aid){
		Admission admit = admissionDao.findByAid(aid);
		if (admit == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			admissionDao.delete(admit);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	
	
	
	private void setAdmissionFields(Map<String, String> body, Admission admit) throws Exception{
		if (body.containsKey("admission") && body.containsKey("discharge")) {
			if (!FieldValidation.compareDates(body.get("admission"), body.get("discharge"))) {
				throw new Exception("Discharge date must come after admission date");
			}
		} else if (admit.getAdmissionDate() != null && body.containsKey("discharge")) {
			if (!FieldValidation.compareDates(admit.getAdmissionDate(), body.get("discharge"))) {
				throw new Exception("Discharge date must come after admission date");
			}
		}
		
		if (body.containsKey("admission")) {
			if (FieldValidation.dateValidation(body.get("admission"))) {
				admit.setAdmissionDate(body.get("admission"));
			} else {
				throw new Exception("Dates must be formatted (yyyy-mm-dd)");
			}
		}
		if (body.containsKey("discharge")) {
			if (FieldValidation.dateValidation(body.get("discharge"))) {
				admit.setDischargeDate(body.get("discharge"));
			} else {
				throw new Exception("Dates must be formatted (yyyy-mm-dd)");
			}
		}
		if (body.containsKey("student")) {
			int studentKey = FieldValidation.parseInt("student", body.get("student"));
			Student student = studentDao.findBySid(studentKey);
			if (student == null) {
				throw new Exception("Student with sid " + studentKey + " could not be found");
			} else {
				admit.setStudent(student);
			}
		}
	}
	
	
	


}
