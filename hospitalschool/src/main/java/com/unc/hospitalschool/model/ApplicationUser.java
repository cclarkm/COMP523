package com.unc.hospitalschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
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
}
