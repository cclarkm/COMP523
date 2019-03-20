package com.unc.hospitalschool.model;

import java.util.HashMap;
import java.util.Map;

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
	
	public Map<String, String> toJson(){
		HashMap<String, String> map = new HashMap<>();
		map.put("gid", Integer.toString(gid));
		map.put("grade", grade);
		
		return map;
	}
	
	@Override
	public String toString() {
		return "Grade: " + grade;
	}
	
	public String getGrade() {
		return grade;
	}
}
