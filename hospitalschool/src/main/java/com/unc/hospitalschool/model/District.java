package com.unc.hospitalschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "district")
public class District {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "did", nullable = false)
	private Long did;
	
	@Column(name = "schoolDistrict", nullable = false)
	private String schoolDistrict;
	
	protected District() {}
	
	public District(String schoolDistrict) {
		this.schoolDistrict = schoolDistrict;
	}
	
	@Override
	public String toString() {
		return "District: " + schoolDistrict;
	}
}
