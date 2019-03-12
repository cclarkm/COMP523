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
	
	
	//get request doesn't display gender - not sure if intentional or not
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
		logger.info(lname);
		logger.info(fname);
		return studentDao.findByFirstNameAndLastName(fname, lname).toJson();
	}
	
	@DeleteMapping(value="/sid={sid}")
	public void deleteBySid(@PathVariable Long sid) {
		studentDao.deleteById(sid);
	}
	
	
	//new 
	@PostMapping(value="/new") //also need to pass in whatever is being changed; could be multiple things
	public Student newStudent(@RequestBody Map<String, String> body) {
		logger.info(body.toString());		
		Integer id = Integer.parseInt(body.get("id"));
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
		
		return studentDao.save(new Student(id, lName, fName, dob, gender, raceEth, servArea,
											school, district, county, grade, studentNotes,
											permissionDate, label, psLabel, currTeach,
											secondTeacher, clinic, hispanic, petTherapy, newYrMessage));
		
		
		//reference to teacherDao with id - retrieve teacher entity
		//student.set(currenTeacher) = what is returned
//		return studentDao.save(new Student());
	}
	
//	//update
//	//i think this may work
//	@PostMapping(value="/sid= {sid}") //also need to pass in whatever is being changed; could be muliple things
//	public Student postBySid(@RequestBody Student student, @PathVariable Long sid) {
//		return studentDao.findById(sid)
//				.map(student -> {
//					student
//				})
//	}
		
}
