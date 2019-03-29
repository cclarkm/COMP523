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
@RequestMapping("/student")
public class StudentController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private GradeDao gradeDao;
	@Autowired
	private CountyDao countyDao;
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private GenderDao genderDao;
	@Autowired
	private PSLabelDao psLabelDao;
	@Autowired
	private RaceEthDao raceEthDao;
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private ServiceAreaDao serviceAreaDao;
	@Autowired
	private TeacherDao teacherDao;
	
	
	@GetMapping("/{sid}")
	@ResponseBody
	public ResponseEntity<?> getStudentById(@PathVariable int sid){
		logger.info("Get student by id called");
		Student studentOptional = studentDao.findBySid(sid);
		if (studentOptional == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(studentOptional.toJson(), HttpStatus.OK);
		}
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllStudents() {
		logger.info("Get all sudents called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Student student: studentDao.findAll()) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	

	@GetMapping(value="/fname={name}")
	public ResponseEntity<Map<String, Object>> getStudentsByFName(@PathVariable String name){
		logger.info("Get sudents called");
		logger.info(name);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Student student: studentDao.findByFirstName(name)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/lname={name}")
	public ResponseEntity<Map<String, Object>> getStudentsByLName(@PathVariable String name){
		logger.info("Get sudents called");
		logger.info(name);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Student student: studentDao.findByLastName(name)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/lname={lname}/fname={fname}")
	public ResponseEntity<Map<String, Object>> getStudentByFirstAndLastName(@PathVariable String lname, @PathVariable String fname){
		logger.info("Get students by first and last name called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Student student: studentDao.findByFirstNameAndLastName(fname, lname)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/*
	 * Works as intended
	 */
	@DeleteMapping(value="/{sid}")
	public ResponseEntity<Object> deleteBySid(@PathVariable int sid) {
		Student studentOptional = studentDao.findBySid(sid);
		if (studentOptional == null) {
			logger.error("Unable to delete - student with sid: " + sid + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			studentDao.deleteById(sid);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	
	//new 
	@PostMapping
	public ResponseEntity<Object> newStudent(@RequestBody Map<String, String> body) {
		logger.info(body.toString());	
		logger.info(body.get("INVALID KEY")); // returns null
		if (!Student.validateFields(body)) {
			logger.info("All required fields not provided");
			return ResponseEntity.badRequest().body("Not all required student parameters provided");
		}
		// START OF REQUIRED FIELDS
		String lName = body.get("lastName");
		body.remove("lastName");
		String fName = body.get("firstName");
		body.remove("firstName");
		String dob = body.get("dob");
		body.remove("dob");
		Gender gender = genderDao.findById(Integer.parseInt(body.get("gender")));
		body.remove("gender");
		RaceEth raceEth = raceEthDao.findByRid(Integer.parseInt(body.get("raceEthnicity")));
		body.remove("raceEthnicity");
		ServiceArea servArea = serviceAreaDao.findBySid(Integer.parseInt(body.get("serviceArea")));
		body.remove("serviceArea");
		School school = schoolDao.findBySid(Integer.parseInt(body.get("school")));
		body.remove("school");
		District district = districtDao.findByDid(Integer.parseInt(body.get("district")));
		body.remove("district");
		County county = countyDao.findByCid(Integer.parseInt(body.get("county")));
		body.remove("county");
		Grade grade = gradeDao.findByGid(Integer.parseInt(body.get("grade")));
		body.remove("grade");
		boolean hispanic = Boolean.parseBoolean(body.get("hispanic"));
		body.remove("hispanic");
		// END OF REQUIRED FIELDS
		
		Student student = new Student(lName, fName, dob, gender, raceEth, servArea, school, district, county, grade, hispanic);
		try {
			setStudentFields(body, student);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
		
		studentDao.save(student);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//update
	@PutMapping(value="/{sid}")
	public ResponseEntity<Object> postBySid(@RequestBody Map<String, String> body, @PathVariable int sid) {
		Student student = studentDao.findBySid(sid);
		if (student == null) {
			logger.error("Unable to update - student with sid: " + sid + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//make sure that if catch, all work is undone
		try {
			setStudentFields(body, student);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
		
		//return studentDao.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void setStudentFields(Map<String, String> body, Student student) {
		for (String x: body.keySet()) {
			switch(x) {
			case "lastName":
				student.setLastName(body.get(x));
				break;
			case "firstName":
				student.setFirstName(body.get(x));
				break;
			case "dob":
				student.setDob(body.get(x));
				break;
			case "gender":
				student.setGender(genderDao.findById(Integer.parseInt(body.get("gender"))));
				break;
			case "raceEthnicity":
				student.setRaceEth(raceEthDao.findByRid(Integer.parseInt(body.get("raceEthnicity"))));
				break;
			case "serviceArea":
				student.setServiceArea(serviceAreaDao.findBySid(Integer.parseInt(body.get("serviceArea"))));
				break;
			case "school":
				student.setSchool(schoolDao.findBySid(Integer.parseInt(body.get("school"))));
				break;
			case "district":
				student.setDistrict(districtDao.findByDid(Integer.parseInt(body.get("district"))));
				break;
			case "county":
				student.setCounty(countyDao.findByCid(Integer.parseInt(body.get("county"))));
				break;
			case "grade":
				student.setGrade(gradeDao.findByGid(Integer.parseInt(body.get("grade"))));
				break;
			case "studentNotes":
				student.setStudentNotes(body.get("studentNotes"));
				break;
			case "permissionDate":
				student.setPermissionDate(body.get("permissionDate"));
				break;
			case "label":
				student.setLabel(body.get("label"));
				break;
			case "psLabel":
				student.setPsLabel(psLabelDao.findByLid(Integer.parseInt(body.get("psLabel"))));
				break;
			case "currentTeacher":
				student.setCurrTeacher(teacherDao.findByTid(Integer.parseInt(body.get("currentTeacher"))));
				break;
			case "secondTeacher":
				student.setSecondTeacher(teacherDao.findByTid(Integer.parseInt(body.get("secondTeacher"))));
				break;
			case "clinic":
				student.setClinic(Boolean.parseBoolean(body.get("clinic")));
				break;
			case "hispanic":
				student.setHispanic(Boolean.parseBoolean(body.get("hispanic")));
				break;
			case "petTherapy":
				student.setPetTherapy(Boolean.parseBoolean(body.get("petTherapy")));
				break;
			case "newYrMessage":
				student.setNewYrMessage(body.get("newYrMessage"));
				break;
			default:
				logger.error("Cannot update " + x + " because the field does not exist on STUDENT");
			}
		}
	}
}
