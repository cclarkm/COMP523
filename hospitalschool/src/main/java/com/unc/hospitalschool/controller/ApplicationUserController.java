package com.unc.hospitalschool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.unc.hospitalschool.dao.ApplicationUserRepository;
import com.unc.hospitalschool.dao.RoleDao;
import com.unc.hospitalschool.model.ApplicationUser;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {
	private static Logger logger = LoggerFactory.getLogger("LOGGER");

	/*
	 * This class has one function: encrypt password of a new user and save it to the database
	 */
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationUserRepository userDao;
    
    @Autowired
    private RoleDao roleDao;
    
    public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

////    @Conditional
//    @PostMapping("/sign-up")
//    public ResponseEntity<ApplicationUser> signUp(@RequestBody ApplicationUser user) {
//    	//need to also know the user making the request to make sure they are an admin
//    	if (userDao.findByUsername(user.getUsername()) != null) {
//    		logger.error("User with that username already exists");
//			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST); // not sure if should return user here as well
//			//including user allows a return of the body that was sent
//    	}
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        logger.info(user.toString());
//        applicationUserRepository.save(user);
//        logger.info("==============================================================================");
//		return new ResponseEntity<>(HttpStatus.OK);
//    }
    
//  @Conditional
    
  @PostMapping("/sign-up")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApplicationUser> signUp(@RequestBody Map<String, String> body) {
  	//need to also know the user making the request to make sure they are an admin
	  logger.warn("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	  ApplicationUser user = new ApplicationUser();
  	if (userDao.findByUsername(body.get("username")) != null) {
  		logger.error("User with that username already exists");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // not sure if should return user here as well
			//including user allows a return of the body that was sent
  	}
  	  user.setUsername(body.get("username"));
      user.setPassword(bCryptPasswordEncoder.encode(body.get("password")));
      user.setRole(roleDao.findByRid(Integer.parseInt(body.get("role"))));
      logger.info(user.toString());
      applicationUserRepository.save(user);
      logger.info("==============================================================================");
		return new ResponseEntity<>(HttpStatus.OK);
  }
    
    //maybe this should also be in authentication
    @PostMapping("/logout")
    public void login(@RequestBody ApplicationUser user) {
    	//maybe somehow add the JWT to a list of those no longer valid; should remove
    		//itself from list after it expires? maybe
    	//when checking login credentials, need to make sure that if the token appears in the list, it is not valid
    
    }
}