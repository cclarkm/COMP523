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
@RequestMapping("/serviceArea")
public class ServiceAreaController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private ServiceAreaDao serviceAreaDao;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllServiceAreas() {
		logger.info("Get all service areas called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (ServiceArea serviceArea: serviceAreaDao.findAll()) {
			jsons.add(serviceArea.toJson());
		}
		map.put("serviceAreas", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping
	public ResponseEntity<Object> newServiceArea(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		if (!body.containsKey("field1")) {
			return ResponseEntity.badRequest().body("field1 field not provided");
		}
		serviceAreaDao.save(new ServiceArea(body.get("field1")));
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@PutMapping(value="/{sid}")
	public ResponseEntity<Object> updateServiceArea(@RequestBody Map<String, String> body, @PathVariable int sid) {
		ServiceArea serviceArea = serviceAreaDao.findBySid(sid);
		logger.info("Updating county " + sid);
		if (serviceArea == null) {
			logger.error("Unable to update - serviceArea with sid: " + sid + " not found");
			return ResponseEntity.badRequest().body("Unable to update - serviceArea with sid: " + sid + " not found");
		}
		if (body.containsKey("field1")) {
			serviceArea.setField1(body.get("field1"));
			serviceAreaDao.save(serviceArea);
			return new ResponseEntity<>(HttpStatus.OK);	
		} else {
			logger.error("Unable to update - serviceArea; incorrect request data");
			return ResponseEntity.badRequest().body("Unable to update - serviceArea. Incorrect request field");
		}
	}
	
	@DeleteMapping(value="/{sid}")
	public ResponseEntity<Object> deleteBySid(@PathVariable int sid) {
		ServiceArea serviceArea = serviceAreaDao.findBySid(sid);
		if (serviceArea == null) {
			logger.error("Unable to delete - serviceArea with sid: " + sid + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - serviceArea with sid: " + sid + " not found");
		} else {
			serviceAreaDao.delete(serviceArea);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
