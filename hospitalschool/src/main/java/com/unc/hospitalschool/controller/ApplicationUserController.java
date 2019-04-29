package com.unc.hospitalschool.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.unc.hospitalschool.dao.ApplicationUserRepository;
import com.unc.hospitalschool.dao.BlackListTokenDao;
import com.unc.hospitalschool.dao.RoleDao;
import com.unc.hospitalschool.model.ApplicationUser;
import com.unc.hospitalschool.model.BlackListToken;
import static com.unc.hospitalschool.security.SecurityConstants.EXPIRATION_TIME;


@RestController
@RequestMapping("/users")
public class ApplicationUserController {

  @Autowired
  private BlackListTokenDao blackListTokenDao;

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

  /*
   * This method allows new users to be created - it must be accessed by someone with a valid access token with admin permissions
   * Checks to make sure the username is unique
   * Tries to create user given the body
   */
  @PostMapping("/sign-up")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApplicationUser> signUp(@RequestBody Map<String, String> body) {
    ApplicationUser user = new ApplicationUser();
    if (userDao.findByUsername(body.get("username")) != null) {
      logger.error("User with that username already exists");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Creating user
    try {
      this.setUserFields(body, user);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    applicationUserRepository.save(user);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /*
   * This method 'logs out' the user by adding their access token to a database
   * Tokens in the database are no longer valid
   */
  @GetMapping("/logout")
  public void login(HttpServletRequest request) {
    String tokenBoi = request.getHeader("Authorization");

    Calendar expires = Calendar.getInstance();
    expires.add(Calendar.MILLISECOND, (int) EXPIRATION_TIME);
    Date expirationTime = expires.getTime();

    BlackListToken token = new BlackListToken(tokenBoi, expirationTime);
    blackListTokenDao.save(token);
    logger.info("Logged out. Token " + token.getTid());
  }

  /*
   * Helper method for sign-up end point for creating a new user
   */
  public void setUserFields(Map<String, String> body, ApplicationUser user) throws Exception {
    for (String x : body.keySet()) {
      switch (x) {
        case "username":
          user.setUsername(body.get(x));
          break;
        case "password":
          user.setPassword(bCryptPasswordEncoder.encode(body.get("password")));
          break;
        case "role":
          user.setRole(roleDao.findByRid(Integer.parseInt(body.get("role"))));
          break;
        default:
          logger.error("Cannot update " + x + " because the field does not exist on USER");
          throw new IllegalArgumentException("Field " + x + " does not exist.");
      }
    }
  }
}
