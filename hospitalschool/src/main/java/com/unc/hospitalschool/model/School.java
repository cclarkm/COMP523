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
@Table(name = "school")
public class School {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "sid", nullable = false)
	private Long sid;
	
	@Column(name = "schoolName", nullable = false)
	private String schoolName;
	
	protected School() {}
	
	public School(String schoolName) {
		this.schoolName = schoolName;
	}
	
	@Override
	public String toString() {
		return "School: " + schoolName;
	}
	
	public String getSchool() {
		return schoolName;
	}
}
