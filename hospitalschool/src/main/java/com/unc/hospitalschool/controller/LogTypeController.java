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
@RequestMapping("/logType")
public class LogTypeController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private LogTypeDao logTypeDao;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllLogTypes() {
		logger.info("Get all log types called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (LogType LogType: logTypeDao.findAll()) {
			jsons.add(LogType.toJson());
		}
		map.put("logTypes", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping
	public ResponseEntity<Object> newLogType(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		logTypeDao.save(new LogType(body.get("logType")));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{lid}")
	public ResponseEntity<Object> updateLogType(@RequestBody Map<String, String> body, @PathVariable int lid) {
		LogType logType = logTypeDao.findByLid(lid);
		logger.info("Updating logType " + lid);
		if (logType == null) {
			logger.error("Unable to update - logType with lid: " + lid + " not found");
			return ResponseEntity.badRequest().body("Unable to oupdate - logType with lid: " + lid + " not found");
		}
		if (body.containsKey("logType")) {
			logType.setLogType(body.get("logType"));
			logTypeDao.save(logType);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Unable to update - logType; incorrect request data");
			return ResponseEntity.badRequest().body("Unable to update - logType. Incorrect request field");
		}
	}
	
	@DeleteMapping(value="/{lid}")
	public ResponseEntity<Object> deleteByLid(@PathVariable int lid) {
		LogType logType = logTypeDao.findByLid(lid);
		if (logType == null) {
			logger.error("Unable to delete - logType with lid: " + lid + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - logType with lid: " + lid + " not found");
		} else {
			logTypeDao.delete(logType);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
