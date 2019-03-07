package com.unc.hospitalschool.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;


@Entity
@Table(name = "students")
public class Student {

	@Id
	@Column(nullable = false)
	private int sid;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column (nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd") //could change - its currently different on access
	private String dob;
	
	@Column (nullable = false)
//	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column (nullable = false)
//	@Enumerated(EnumType.STRING)
	private RaceEth raceEth;
//	private int raceEth;
	
//	@Column (nullable = true)
//	private String service;
		
	@Column (nullable = false)
	private ServiceArea serviceArea;
	
	@Column (nullable = false)
	private int school;
	
	@Column (nullable = false)
	private int district;
	
	@Column (nullable = false)
	private int county;
	
	@Column (nullable = false)
	private int grade;
	
	@Column (nullable = false)
	private String studentNotes;
	
	@Column (nullable = false)
	private String permissionDate;
	
	@Column (nullable = true)
	private String label;
	
	@Column (nullable = true)
	private int psLabel;
	
	@Column (nullable = false)
	private String currTeacher;
	
	@Column (nullable = false)
	private String secondTeacher;
	
	@Column (nullable = false)
	private boolean clinic;
	
	@Column (nullable = false)
	private boolean hispanic;
	
	@Column (nullable = false)
	private boolean petTherapy;
	
	@Column (nullable = true)
	private int newYrMessage;
	
//	@Column (nullable = false)
//	private int countDates;
	
	public Student(int sid, String lastName, String firstName, String dob, 
			Gender gender, RaceEth raceEth, ServiceArea serviceArea,
			School school, District district, County county, Grade grade, 
			String studentNotes, String permissionDate, Label label,
			PSLabel psLabel, Teacher currTeacher, Teacher secondTeacher,
			boolean clinic, boolean hispanic, boolean petTherapy, 
			NewYrMessage newYrMessage) {
this.sid = sid;
this.lastName = lastName;
this.firstName = firstName;
this.dob = dob;
this.gender = gender;
this.raceEth = raceEth;
this.serviceArea = serviceArea;
this.school = school;
this.district = district;
this.county = county;
this.grade = grade;
this.studentNotes = studentNotes;
this.permissionDate = permissionDate;
this.label = label;
this.psLabel = psLabel;
this.currTeacher = currTeacher;
this.secondTeacher = secondTeacher;
this.clinic = clinic;
this.hispanic = hispanic;
this.petTherapy = petTherapy;
this.newYrMessage = newYrMessage;
}
}
