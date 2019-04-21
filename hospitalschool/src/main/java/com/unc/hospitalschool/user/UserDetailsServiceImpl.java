package com.unc.hospitalschool.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.unc.hospitalschool.dao.ApplicationUserRepository;
import com.unc.hospitalschool.model.ApplicationUser;
import com.unc.hospitalschool.model.Role;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;

	private static Logger logger = LoggerFactory.getLogger("LOGGER");

    
    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(username);
		}
		
		//added
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		String grantedAuthorities = applicationUser.getRole().getRole(); //returns string of role
		logger.info("YO " + grantedAuthorities);

		roles.add(new SimpleGrantedAuthority("ROLE_" + grantedAuthorities));
		logger.info("ROLES " + roles);

		//added
		User user = new User(applicationUser.getUsername(), applicationUser.getPassword(), roles);
		return user;

	}
}