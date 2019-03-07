package com.unc.hospitalschool.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

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

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd") // could change - its currently different on access
	private String dob;

	@ManyToOne
	@JoinColumn(name = "gender")
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "raceEth")
	private RaceEth raceEth;

//	@Column (nullable = true)
//	private String service;

	@ManyToOne
	@JoinColumn(name = "serviceArea")
	private ServiceArea serviceArea;

	
	@ManyToOne
	@JoinColumn(name = "school")
	private School school;

	@ManyToOne
	@JoinColumn(name = "district")
	private District district;

	@ManyToOne
	@JoinColumn(name = "county")
	private County county;

	@ManyToOne
	@JoinColumn(name = "grade")
	private Grade grade;

	@Column(nullable = false)
	private String studentNotes;

	@Column(nullable = false)
	private String permissionDate;

	@Column(nullable = true)
	private String label;

	@ManyToOne
	@JoinColumn(name = "psLabel")
	private PSLabel psLabel;

	@ManyToOne
	@JoinColumn(name = "currTeacher")
	private Teacher currTeacher;

	@ManyToOne
	@JoinColumn(name = "secondTeacher")
	private Teacher secondTeacher;

	@Column(nullable = false)
	private boolean clinic;

	@Column(nullable = false)
	private boolean hispanic;

	@Column(nullable = false)
	private boolean petTherapy;

	@Column(nullable = true)
	private String newYrMessage;

//	@Column (nullable = false)
//	private int countDates;

	public Student(int sid, String lastName, String firstName, String dob, Gender gender, RaceEth raceEth,
			ServiceArea serviceArea, School school, District district, County county, Grade grade, String studentNotes,
			String permissionDate, String label, PSLabel psLabel, Teacher currTeacher, Teacher secondTeacher,
			boolean clinic, boolean hispanic, boolean petTherapy, String newYrMessage) {
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
	
	public Student() {
		
	}
}
