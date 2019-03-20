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
	
	@GetMapping(value = "/")
	@ResponseBody
	public Map<String, Object> getAllCounties() {
		logger.info("Get all counties called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (ServiceArea serviceArea: serviceAreaDao.findAll()) {
			jsons.add(serviceArea.toJson());
		}
		map.put("serviceAreas", jsons);
		return map;	
	}

	@PostMapping(value="/new") //also need to pass in whatever is being changed; could be multiple things
	public ServiceArea newServiceArea(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return serviceAreaDao.save(new ServiceArea(body.get("field1")));

	}
	
	
}