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
@RequestMapping("/district")
public class DistrictController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private DistrictDao districtDao;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllDistrict() {
		logger.info("Get all districts called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (District district: districtDao.findAll()) {
			jsons.add(district.toJson());
		}
		map.put("districts", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping
	public ResponseEntity<Object> newDistrict(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		if (!body.containsKey("district")) {
			return ResponseEntity.badRequest().body("district field not provided");
		}
		districtDao.save(new District(body.get("district")));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{did}")
	public ResponseEntity<Object> updateDistrict(@RequestBody Map<String, String> body, @PathVariable int did) {
		District district = districtDao.findByDid(did);
		logger.info("Updating district " + did);
		if (district == null) {
			logger.error("Unable to update - district with did: " + did);
			return ResponseEntity.badRequest().body("Unable to update - district with did: " + did + " not found");
		}
		if (body.containsKey("district")) {
			district.setDistrict(body.get("district"));
			districtDao.save(district);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Unable to update - district: incorrect request data");
			return ResponseEntity.badRequest().body("Unable to update - district. Incorrect request field");
		}		
	}
	
	@DeleteMapping(value="/{did}")
	public ResponseEntity<Object> deleteByDid(@PathVariable int did) {
		District district = districtDao.findByDid(did);
		if (district == null) {
			logger.error("Unable to delete - district with did: " + did + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - district with did: " + did + " not found");
		} else {
			districtDao.delete(district);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
