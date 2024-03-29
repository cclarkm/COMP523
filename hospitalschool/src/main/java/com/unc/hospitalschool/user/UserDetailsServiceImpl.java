package com.unc.hospitalschool.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.unc.hospitalschool.dao.ApplicationUserRepository;
import com.unc.hospitalschool.model.ApplicationUser;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private ApplicationUserRepository applicationUserRepository;


  public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
    this.applicationUserRepository = applicationUserRepository;
  }

  /*
   * Creates new user instance based on an existing ApplicationUser
   * Adds roles so that users can only access end points of their role's access level
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
    if (applicationUser == null) {
      throw new UsernameNotFoundException(username);
    }

    //Creates authorities for users
    Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
    String grantedAuthorities = applicationUser.getRole().getRole(); // returns string of role
    roles.add(new SimpleGrantedAuthority("ROLE_" + grantedAuthorities));

    //Creates new user based on username, password, and set of roles
    User user = new User(applicationUser.getUsername(), applicationUser.getPassword(), roles);
    return user;

  }
}
