package com.unc.hospitalschool.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.unc.hospitalschool.dao.SchoolDao;
import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.dao.TestDao;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@ComponentScan(basePackages= {"com.unc.hospitalschool"})
@EntityScan(basePackages= {"com.unc.hospitalschool.model"})
@EnableJpaRepositories(basePackages= {"com.unc.hospitalschool.dao"})
public class HospitalschoolApplication{

	private static Logger logger = LoggerFactory.getLogger("LOGGER");

	
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(HospitalschoolApplication.class,  args);
		
	}


}