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
@RequestMapping("/raceEth")
public class RaceEthController {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	

	@Autowired
	private RaceEthDao raceEthDao;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllRaceEth() {
		logger.info("Get all raceEths called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();
		
		for (RaceEth raceeth: raceEthDao.findAll()) {
			jsons.add(raceeth.toJson());
		}
		map.put("raceEths", jsons);
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping
	public ResponseEntity<Object> newRaceEth(@RequestBody Map<String, String> body) {
		logger.info(body.toString());
		if (!(body.containsKey("raceEth") && body.containsKey("code"))) {
			return ResponseEntity.badRequest().body("raceEth/code field not provided");
		}
		raceEthDao.save(new RaceEth(body.get("raceEth"), body.get("code")));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value="/{rid}")
	public ResponseEntity<Object> updateRaceEth(@RequestBody Map<String, String> body, @PathVariable int rid) {
		RaceEth raceEth = raceEthDao.findByRid(rid);
		logger.info("Updating county " + rid);
		if (raceEth == null) {
			logger.error("Unable to update - raceEthd with rid: " + rid + " not found");
			return ResponseEntity.badRequest().body("Unable to update - raceEth with rid: " + rid + " not found");
		}
		if (!body.containsKey("raceEth") && !body.containsKey("code")) {
			logger.error("Unable to update - raceEth; incorrect request data");
			return ResponseEntity.badRequest().body("Unable to update - raceEth. Incorrect request field");
		}
		if (body.containsKey("raceEth")) {
			raceEth.setRaceEth(body.get("raceEth"));
		}
		if (body.containsKey("code")) {
			raceEth.setCode(body.get("code"));
		}
		raceEthDao.save(raceEth);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{rid}")
	public ResponseEntity<Object> deleteByRid(@PathVariable int rid) {
		RaceEth raceEth = raceEthDao.findByRid(rid);
		if (raceEth == null) {
			logger.error("Unable to delete - raceEth with rid: " + rid + " not found");
			return ResponseEntity.badRequest().body("Unable to delete - raceEth with rid: " + rid + " not found");
		} else {
			raceEthDao.delete(raceEth);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}	
}
