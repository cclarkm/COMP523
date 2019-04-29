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
@RequestMapping("/school")
public class SchoolController {

  private static Logger logger = LoggerFactory.getLogger("LOGGER");


  @Autowired
  private SchoolDao schoolDao;

  @GetMapping
  @ResponseBody
  public ResponseEntity<Map<String, Object>> getAllSchools() {
    logger.info("Get all schools called");
    Map<String, Object> map = new HashMap<String, Object>();
    List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();

    for (School school : schoolDao.findAll()) {
      jsons.add(school.toJson());
    }
    map.put("schools", jsons);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> newSchool(@RequestBody Map<String, String> body) {
    logger.info(body.toString());
    if (!body.containsKey("school")) {
      return ResponseEntity.badRequest().body("school field not provided");
    }
    schoolDao.save(new School(body.get("school")));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(value = "/{sid}")
  public ResponseEntity<Object> updateSchool(@RequestBody Map<String, String> body,
      @PathVariable int sid) {
    School school = schoolDao.findBySid(sid);
    logger.info("Updating school " + sid);
    if (school == null) {
      logger.error("Unable to update - school with sid: " + sid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to update - school with sid: " + sid + " not found");
    }
    if (body.containsKey("school")) {
      school.setSchool(body.get("school"));
      schoolDao.save(school);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      logger.error("Unable to update - school; incorrect request data");
      return ResponseEntity.badRequest().body("Unable to update - school. Incorrect request field");
    }
  }

  @DeleteMapping(value = "/{sid}")
  public ResponseEntity<Object> deleteBySid(@PathVariable int sid) {
    School school = schoolDao.findBySid(sid);
    if (school == null) {
      logger.error("Unable to delete - school with sid: " + sid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to delete - school with sid: " + sid + " not found");
    } else {
      schoolDao.delete(school);
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }
}
