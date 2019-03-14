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

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="admissions")
public class Admissions {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "aid", nullable = false)
	private int aid;
	
	@Column(name = "sid", nullable = false)
	@JsonProperty(value = "sid")
	private Student student;
	
	@Column(name = "admission", nullable = false)
	private String admissionDate;
	
	@Column(name = "discharge", nullable = true)
	private String dischargeDate;
	
	protected Admissions() {}
	
	public Admissions(int aid, Student student, String adminDate, String disDate) {
		this.aid = aid;
		this.student = student;
		this.admissionDate = adminDate;
		this.dischargeDate = disDate;
	}

	public Map<String, String> toJson() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("aid", Integer.toString(this.aid));
		map.put("sid", this.student.getFullName());
		map.put("admissionDate", this.admissionDate);
		map.put("dischargeDate", this.dischargeDate);
		return map;
	}
	
	public void setAid(int aid) {
		this.aid = aid;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	
	@Override
	public String toString() {
		return "Admissions [aid=" + this.aid + ", student=" + this.student.getFullName() +
				", admissionDate=" + this.admissionDate + ", dischargeDate=" + this.dischargeDate;
	}
}
