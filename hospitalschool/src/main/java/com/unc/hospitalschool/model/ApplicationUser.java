package com.unc.hospitalschool.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="ApplicationUser")
public class ApplicationUser {
	
    @Id
	@Column(nullable = false, name="userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
	@JsonProperty(value = "username")
    private String username;
    
    @Column(nullable = false)
	@JsonProperty(value = "password")
    private String password;

    @ManyToOne
	@JoinColumn(name = "role")
	@JsonProperty(value = "role")
    private Role role;
        
    public ApplicationUser() {}
    
    //primary identifier of a user
    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Role getRole() {
    	return this.role;
    }
    
    public void setRole(Role role) {
    	this.role = role;
    }
    
    public boolean isAdmin() {
    	return (this.role.equals("ADMIN"));
    }
    
    public String toString() {
    	return "\nID: " + id + "\n USERNAME: " + username + "\n PASSWORD: " + password + "\n ROLE: " + role.getRole();
    }
//    public UserDetails toSecurityUserDetails() {
//    	return SecurityUserDetails.create(this);
//    }

}
