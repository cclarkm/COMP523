package com.unc.hospitalschool.controller;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@RequestMapping("/token")
public class BlackListTokenController {

private static Logger logger = LoggerFactory.getLogger("LOGGER");
	
	@Autowired
	private BlackListTokenDao blackListTokenDao;
	
	@DeleteMapping
	public ResponseEntity<Object> deleteByExpDate() {
		List<BlackListToken> tokens = blackListTokenDao.findAll();
		for (BlackListToken x: tokens) {
			Date expiration = x.getDate();
			Calendar now = Calendar.getInstance();
			Date currentTime = now.getTime();
			
			//It has expired, so it does not need to be blacklisted anymore
			if (expiration.before(currentTime) || expiration.equals(currentTime)) {
				blackListTokenDao.delete(x);
				logger.info("Deleted token with tid of " + x.getTid());
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
