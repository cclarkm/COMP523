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
@RequestMapping("/logType")
public class LogTypeController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private LogTypeDao logTypeDao;
	
	@GetMapping
	@ResponseBody
	public Map<String, Object> getAllLogTypes() {
		logger.info("Get all log types called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (LogType LogType: logTypeDao.findAll()) {
			jsons.add(LogType.toJson());
		}
		map.put("logTypes", jsons);
		return map;	
	}

	@PostMapping
	public LogType newLogType(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		return logTypeDao.save(new LogType(body.get("logType")));
	}
	
	@PutMapping(value="/{lid}")
	public LogType updateLogType(@RequestBody Map<String, String> body, @PathVariable int lid) {
		LogType logType = logTypeDao.findByLid(lid);
		logger.info("Updating logType " + lid);
		if (logType == null) {
			logger.error("Unable to update - logType with lid: " + lid + " not found");
			return null;
		}
		if (body.containsKey("logType")) {
			logType.setLogType(body.get("logType"));
			return logTypeDao.save(logType);
		} else {
			logger.error("Unable to update - logType; incorrect request data");
			return null;
		}
	}
	
	@DeleteMapping(value="/{lid}")
	public void deleteByLid(@PathVariable int lid) {
		LogType logType = logTypeDao.findByLid(lid);
		if (logType == null) {
			logger.error("Unable to delete - logType with lid: " + lid + " not found");
		} else {
			logTypeDao.delete(logType);
		}
	}
}
