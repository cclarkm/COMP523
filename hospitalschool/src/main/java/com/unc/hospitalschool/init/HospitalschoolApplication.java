package com.unc.hospitalschool.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.unc.hospitalschool.dao.SchoolDao;
import com.unc.hospitalschool.dao.StudentDao;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan(basePackages= {"com.unc.hospitalschool"})
@EntityScan(basePackages= {"com.unc.hospitalschool.model"})
@EnableJpaRepositories(basePackages= {"com.unc.hospitalschool.dao"})
public class HospitalschoolApplication{

	private static Logger logger = LoggerFactory.getLogger("LOGGER");

<<<<<<< HEAD
		
=======
	//not sure if this is the right place for this but idk:
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
>>>>>>> 59d44591ea418fd9e4ddb812d6a502da052e28ed
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(HospitalschoolApplication.class,  args);
//		SpringApplication.run(SpringBootAuthUpdatedApplication.class, args);

	}


}
