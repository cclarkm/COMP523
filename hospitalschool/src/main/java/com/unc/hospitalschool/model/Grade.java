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
@Table(name = "grade")
public class Grade {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "gid", nullable = false)
	private int gid;
	
	@Column(name = "grade", nullable = false)
	private String grade;
	
	protected Grade() {}
	
	public Grade(String grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		return "Grade: " + grade;
	}
	
	public String getGrade() {
		return grade;
	}
}
