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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unc.hospitalschool.dao.CountyDao;
import com.unc.hospitalschool.dao.DistrictDao;
import com.unc.hospitalschool.dao.GenderDao;
import com.unc.hospitalschool.dao.GradeDao;
import com.unc.hospitalschool.dao.PSLabelDao;
import com.unc.hospitalschool.dao.RaceEthDao;
import com.unc.hospitalschool.dao.SchoolDao;
import com.unc.hospitalschool.dao.ServiceAreaDao;
import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.dao.TeacherDao;
import com.unc.hospitalschool.model.Student;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	public ResponseEntity<?> getStudentById(@PathVariable int sid) {
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

		for (Student student : studentDao.findAll()) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(value = "/fname={name}")
	public ResponseEntity<Map<String, Object>> getStudentsByFName(@PathVariable String name) {
		logger.info("Get sudents called");
		logger.info(name);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();

		for (Student student : studentDao.findByFirstName(name)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(value = "/lname={name}")
	public ResponseEntity<Map<String, Object>> getStudentsByLName(@PathVariable String name) {
		logger.info("Get sudents called");
		logger.info(name);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();

		for (Student student : studentDao.findByLastName(name)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(value = "/lname={lname}/fname={fname}")
	public ResponseEntity<Map<String, Object>> getStudentByFirstAndLastName(@PathVariable String lname,
			@PathVariable String fname) {
		logger.info("Get students by first and last name called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();

		for (Student student : studentDao.findByFirstNameAndLastName(fname, lname)) {
			jsons.add(student.toJson());
		}
		map.put("students", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/*
	 * Works as intended
	 */
	@DeleteMapping(value = "/{sid}")
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

	// new
	@PostMapping
	public ResponseEntity<Object> newStudent(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		if (!Student.validateFields(body)) {
			logger.info("All required fields not provided");
			return ResponseEntity.badRequest().body("Not all required student parameters provided");
		}
		Student student = new Student();

		// need to also have a thing where if there are extra fields, there is an error,
		// maybe?
		try {
			setStudentFields(body, student);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		studentDao.save(student);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	// update
	@PostMapping(value = "/{sid}")
	public ResponseEntity<Object> postBySid(@RequestBody Map<String, String> body, @PathVariable int sid) {
		Student student = studentDao.findBySid(sid);
		if (student == null) {
			logger.error("Unable to update - student with sid: " + sid + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			setStudentFields(body, student);
		} catch (Exception e) {
			// e.getMessage()
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		studentDao.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void setStudentFields(Map<String, String> body, Student student) throws Exception {
		for (String x : body.keySet()) {
			switch (x) {
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
				student.setGender(genderDao.findById(parseInt("gender", body.get("gender"))));
				break;
			case "raceEthnicity":
				student.setRaceEth(raceEthDao.findByRid(parseInt("raceEthnicity", body.get("raceEthnicity"))));
				break;
			case "serviceArea":
				student.setServiceArea(serviceAreaDao.findBySid(parseInt("serviceArea", body.get("serviceArea"))));
				break;
			case "school":
				student.setSchool(schoolDao.findBySid(parseInt("school", body.get("school"))));
				break;
			case "district":
				student.setDistrict(districtDao.findByDid(parseInt("district", body.get("district"))));
				break;
			case "county":
				student.setCounty(countyDao.findByCid(parseInt("county", body.get("county"))));
				break;
			case "grade":
				student.setGrade(gradeDao.findByGid(parseInt("grade", body.get("grade"))));
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
				student.setPsLabel(psLabelDao.findByLid(parseInt("psLabel", body.get("psLabel"))));
				break;
			case "currentTeacher":
				student.setCurrTeacher(teacherDao.findByTid(parseInt("currentTeacher", body.get("currentTeacher"))));
				break;
			case "secondTeacher":
				student.setSecondTeacher(teacherDao.findByTid(parseInt("secondTeacher", body.get("secondTeacher"))));
				break;
			case "clinic":
				student.setClinic(parseBoolean("clinic", body.get("clinic")));
				break;
			case "hispanic":
				student.setHispanic(parseBoolean("hispanic", body.get("hispanic")));
				break;
			case "petTherapy":
				student.setPetTherapy(parseBoolean("petTherapy", body.get("petTherapy")));
				break;
			case "newYrMessage":
				student.setNewYrMessage(body.get("newYrMessage"));
				break;
			default:
				logger.error("Cannot update " + x + " because the field does not exist on STUDENT");
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
