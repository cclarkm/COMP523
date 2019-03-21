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
@RequestMapping("/county")
public class CountyController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private CountyDao countyDao;
	
	@GetMapping(value = "/")
	@ResponseBody
	public Map<String, Object> getAllCounties() {
		logger.info("Get all counties called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (County county: countyDao.findAll()) {
			jsons.add(county.toJson());
		}
		map.put("counties", jsons);
		return map;	
	}

	@PostMapping(value="/new")
	public County newCounty(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return countyDao.save(new County(body.get("county")));
	}
	
	@PutMapping(value="/update/cid={cid}")
	public County postByCid(@RequestBody Map<String, String> body, @PathVariable int cid) {
		County county = countyDao.findByCid(cid);
		logger.info("Updating county " + cid);
		if (county == null) {
			logger.error("Unable to update - county with cid: " + cid + " not found");
			return null;
		}
		if (body.containsKey("county")) {
			county.setCounty(body.get("county"));
			return countyDao.save(county);
		} else {
			logger.error("Unable to update - county; incorrect request data");
			return null;
		}
	}
	
	@DeleteMapping(value="/delete/cid={cid}")
	public void deleteByCid(@PathVariable int cid) {
		County county = countyDao.findByCid(cid);
		if (county == null) {
			logger.error("Unable to delete - county with cid: " + cid + " not found");
		} else {
			countyDao.delete(county);
		}
	}	
}
