//package com.unc.hospitalschool.user;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class SecurityUser implements UserDetails {
//
//	String ROLE_PREFIX = "ROLE_";
//
//	String userName;
//	String password;
//	String role;
//
//	public SecurityUser(String username, String password, String role) {
//		this.userName = username;
//		this.password = password;
//		this.role = role;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
//
//		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
//
//		return list;
//	}
//
////	public static UserDetails create(ApplicationUser entity) {
////	    System.out.println(entity.getUserID()+ entity.getEmailAddress()+ entity.getPassword()+ entity.getRole());
////	    return new SecurityUserDetails(entity.getUserID(), entity.getEmailAddress(), entity.getPassword(), entity.getRole());
////	}
//	
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//}
