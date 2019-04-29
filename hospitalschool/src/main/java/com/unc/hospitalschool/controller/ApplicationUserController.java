package com.unc.hospitalschool.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
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

  public static List<String> blackListOfTokens = new ArrayList<String>();

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


  @PostMapping("/sign-up")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApplicationUser> signUp(@RequestBody Map<String, String> body) {
    // need to also know the user making the request to make sure they are an admin
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
