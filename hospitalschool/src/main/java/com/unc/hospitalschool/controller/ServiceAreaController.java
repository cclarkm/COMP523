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
@RequestMapping("/serviceArea")
public class ServiceAreaController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private ServiceAreaDao serviceAreaDao;
	
	@GetMapping
	@ResponseBody
	public Map<String, Object> getAllServiceAreas() {
		logger.info("Get all service areas called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (ServiceArea serviceArea: serviceAreaDao.findAll()) {
			jsons.add(serviceArea.toJson());
		}
		map.put("serviceAreas", jsons);
		return map;	
	}

	@PostMapping
	public ServiceArea newServiceArea(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return serviceAreaDao.save(new ServiceArea(body.get("field1")));
	}
	
	@PutMapping(value="/{sid}")
	public ServiceArea updateServiceArea(@RequestBody Map<String, String> body, @PathVariable int sid) {
		ServiceArea serviceArea = serviceAreaDao.findBySid(sid);
		logger.info("Updating county " + sid);
		if (serviceArea == null) {
			logger.error("Unable to update - serviceArea with sid: " + sid + " not found");
			return null;
		}
		if (body.containsKey("field1")) {
			serviceArea.setField1(body.get("field1"));
			return serviceAreaDao.save(serviceArea);
		} else {
			logger.error("Unable to update - serviceArea; incorrect request data");
			return null;
		}
	}
	
	@DeleteMapping(value="/{sid}")
	public void deleteBySid(@PathVariable int sid) {
		ServiceArea serviceArea = serviceAreaDao.findBySid(sid);
		if (serviceArea == null) {
			logger.error("Unable to delete - serviceArea with sid: " + sid + " not found");
		} else {
			serviceAreaDao.delete(serviceArea);
		}
	}
}
