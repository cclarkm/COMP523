package com.unc.hospitalschool.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gender")
public class Gender {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "gid", nullable = false)
	private int id;
	
	@Column(name = "gender", nullable = false)
	private String gender;
	
	protected Gender() {}
	
	public Gender(String gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return "Gender: " + gender;
	}
	
	public String getGender() {
		return gender;
	}
	
	
}
