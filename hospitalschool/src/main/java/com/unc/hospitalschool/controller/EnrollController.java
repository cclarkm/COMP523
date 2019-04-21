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
@RequestMapping("/enroll")
public class EnrollController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private EnrollDao enrollDao;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllEnroll() {
		logger.info("Get all enrolls called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (Enroll enroll: enrollDao.findAll()) {
			jsons.add(enroll.toJson());
		}
		map.put("enroll", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	
	@PostMapping
	public ResponseEntity<Object> newEnroll(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		enrollDao.save(new Enroll(body.get("enroll")));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{eid}")
	public ResponseEntity<Object> updateEnroll(@RequestBody Map<String, String> body, @PathVariable int eid) {
		Enroll enroll = enrollDao.findByEid(eid);
		logger.info("Updating enroll " + eid);
		if (enroll == null) {
			logger.error("Unable to update - district with did: " + eid);
			return ResponseEntity.badRequest().body("Unable to update - district with eid: " + eid + " not found");
		}
		
		if (body.containsKey("enroll")) {
			enroll.setEnroll(body.get("enroll"));
			enrollDao.save(enroll);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Unable to update - enroll: incorrect request data");
			return ResponseEntity.badRequest().body("Unable to udpate - enroll. Incorrect request field");
		}		
	}
	
	@DeleteMapping(value="/{eid}")
	public ResponseEntity<Object> deleteByEid(@PathVariable int eid) {
		Enroll enroll = enrollDao.findByEid(eid);
		if (enroll == null) {
			logger.error("Unable to delete - enroll with eid: " + eid + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - enroll with eid: " + eid + " not found");
		} else {
			enrollDao.delete(enroll);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
}
