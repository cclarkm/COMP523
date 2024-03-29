package com.unc.hospitalschool.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.unc.hospitalschool.dao.ApplicationUserRepository;
import com.unc.hospitalschool.dao.BlackListTokenDao;
import com.unc.hospitalschool.model.ApplicationUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import static com.unc.hospitalschool.security.SecurityConstants.HEADER_STRING;
import static com.unc.hospitalschool.security.SecurityConstants.SECRET;
import static com.unc.hospitalschool.security.SecurityConstants.TOKEN_PREFIX;

/*
 * Disclaimer: we did not write this code - it came from Auth0
 * However, we did make significant changes to it, which have been commented
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


  private ApplicationUserRepository userDao;

  private BlackListTokenDao blackListTokenDao;

  public JWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain) throws IOException, ServletException {

    //Check if the userDao is set up
    if (userDao == null) {
      ServletContext servletContext = req.getServletContext();
      WebApplicationContext webApplicationContext =
          WebApplicationContextUtils.getWebApplicationContext(servletContext);
      userDao = webApplicationContext.getBean(ApplicationUserRepository.class);
    }
    //Check is the blackListTokenDao is set up
    if (blackListTokenDao == null) {
      ServletContext servletContext = req.getServletContext();
      WebApplicationContext webApplicationContext =
          WebApplicationContextUtils.getWebApplicationContext(servletContext);
      blackListTokenDao = webApplicationContext.getBean(BlackListTokenDao.class);
    }

    String header = req.getHeader(HEADER_STRING);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    //Check the database for the token; if it is blacklisted, it is invalid
    if (this.blackListTokenDao.findByToken(header) != null) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      // parse the token.
      String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
          .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

      ApplicationUser appUser = userDao.findByUsername(user);

      //Get the roles of the user
      if (user != null) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(appUser.getRole().getRole()));
        return new UsernamePasswordAuthenticationToken(user, null, roles);
      }
      return null;
    }
    return null;
  }
}
