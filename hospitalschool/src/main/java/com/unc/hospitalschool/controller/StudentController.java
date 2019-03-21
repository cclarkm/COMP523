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
	
	@GetMapping(value="/lname={lname}/fname={fname}")
	public Map<String, String> getStudentByFirstAndLastName(@PathVariable String lname, @PathVariable String fname){
		logger.info(lname);
		logger.info(fname);
		return studentDao.findByFirstNameAndLastName(fname, lname).toJson();
	}
	
	@DeleteMapping(value="/delete/sid={sid}")
	public void deleteBySid(@PathVariable int sid) {
		Student studentOptional = studentDao.findBySid(sid);
		if (studentOptional == null) {
			logger.error("Unable to delete - student with sid: " + sid + " not found");
		} else {
			studentDao.deleteById(sid);
		}
	}
	
	
	//new 
	@PostMapping(value="/new")
	public Student newStudent(@RequestBody Map<String, String> body) {
		logger.info(body.toString());		
		String lName = body.get("lastName");
		String fName = body.get("firstName");
		String dob = body.get("dob");

		Gender gender = genderDao.findById(Integer.parseInt(body.get("gender")));
		RaceEth raceEth = raceEthDao.findByRid(Integer.parseInt(body.get("raceEthnicity")));
		ServiceArea servArea = serviceAreaDao.findBySid(Integer.parseInt(body.get("serviceArea")));
		School school = schoolDao.findBySid(Integer.parseInt(body.get("school")));
		District district = districtDao.findByDid(Integer.parseInt(body.get("district")));
		County county = countyDao.findByCid(Integer.parseInt(body.get("county")));
		Grade grade = gradeDao.findByGid(Integer.parseInt(body.get("grade")));
		String studentNotes = body.get("studentNotes");
		String permissionDate = body.get("permissionDate");
		String label = body.get("label");
		PSLabel psLabel = psLabelDao.findByLid(Integer.parseInt(body.get("psLabel")));
		Teacher currTeach = teacherDao.findByTid(Integer.parseInt(body.get("currentTeacher")));
		Teacher secondTeacher = teacherDao.findByTid(Integer.parseInt(body.get("secondTeacher")));
		boolean clinic = Boolean.parseBoolean(body.get("clinic"));
		boolean hispanic = Boolean.parseBoolean(body.get("hispanic"));
		boolean petTherapy = Boolean.parseBoolean(body.get("petTherapy"));
		String newYrMessage = body.get("newYrMessage");
		
		return studentDao.save(new Student(lName, fName, dob, gender, raceEth, servArea,
											school, district, county, grade, studentNotes,
											permissionDate, label, psLabel, currTeach,
											secondTeacher, clinic, hispanic, petTherapy, newYrMessage));
	}
	
	//update
	@PutMapping(value="/update/sid={sid}")
	public Student postBySid(@RequestBody Map<String, String> body, @PathVariable int sid) {
		Student student = studentDao.findBySid(sid);
		if (student == null) {
			logger.error("Unable to update - student with sid: " + sid + " not found");
			return null; //return an error here
		}
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
		return studentDao.save(student);
	}
}
