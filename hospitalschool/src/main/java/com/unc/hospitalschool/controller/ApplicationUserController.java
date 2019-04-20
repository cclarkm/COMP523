package com.unc.hospitalschool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.unc.hospitalschool.dao.ApplicationUserRepository;
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
    
    public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @Conditional
    @PostMapping("/sign-up")
    public ResponseEntity<ApplicationUser> signUp(@RequestBody ApplicationUser user) {
    	//need to also know the user making the request to make sure they are an admin
    	if (userDao.findByUsername(user.getUsername()) != null) {
    		logger.error("User with that username already exists");
			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST); // not sure if should return user here as well
			//including user allows a return of the body that was sent
    	}
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
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