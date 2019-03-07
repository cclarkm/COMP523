package com.unc.hospitalschool.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unc.hospitalschool.dao.StudentRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class HospitalschoolApplication{

	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	
	//@Autowired
	//static StudentRepository studentDao;
	
	
	public static void main(String[] args) {
		logger.info("STARTING APPLICATION");
		//studentDao.findAll();
		SpringApplication.run(HospitalschoolApplication.class, args);
		logger.info("ENDING APPLICATION");
		
	}


}
