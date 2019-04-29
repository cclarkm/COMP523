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
@RequestMapping("/teacher")
public class TeacherController {

  private static Logger logger = LoggerFactory.getLogger("LOGGER");


  @Autowired
  private TeacherDao teacherDao;

  @GetMapping
  @ResponseBody
  public ResponseEntity<Map<String, Object>> getAllTeachers() {
    logger.info("Get all teachers called");
    Map<String, Object> map = new HashMap<String, Object>();
    List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();

    for (Teacher Teacher : teacherDao.findAll()) {
      jsons.add(Teacher.toJson());
    }
    map.put("teachers", jsons);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> newTeacher(@RequestBody Map<String, String> body) {
    logger.info(body.toString());
    if (!(body.containsKey("lName") && body.containsKey("fName"))) {
      return ResponseEntity.badRequest().body("fName/lName field not provided");
    }
    teacherDao.save(new Teacher(body.get("lName"), body.get("fName")));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(value = "/{tid}")
  public ResponseEntity<Object> updateTeacher(@RequestBody Map<String, String> body,
      @PathVariable int tid) {
    Teacher teacher = teacherDao.findByTid(tid);
    logger.info("Updating teacher " + tid);
    if (teacher == null) {
      logger.error("Unable to update - teacher with tid: " + tid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to update - teacher with tid: " + tid + " not found");
    }
    if (!(body.containsKey("fName") && body.containsKey("lName"))) {
      logger.error("Unable to update - county; incorrect request data");
      return ResponseEntity.badRequest()
          .body("Unable to update - teacher. Incorrect request field");
    }

    teacher.setFirstName(body.get("fName"));

    teacher.setLastName(body.get("lName"));

    teacherDao.save(teacher);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{tid}")
  public ResponseEntity<Object> deleteByTid(@PathVariable int tid) {
    Teacher teacher = teacherDao.findByTid(tid);
    if (teacher == null) {
      logger.error("Unable to delete - teacher with tid: " + tid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to delete - teacher with tid: " + tid + " not found");
    } else {
      teacherDao.delete(teacher);
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }
}
