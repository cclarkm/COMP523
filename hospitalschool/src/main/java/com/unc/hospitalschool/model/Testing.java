package com.unc.hospitalschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testing")
public class Testing {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "tid", nullable = false)
	private Long tid;
	
	@Column(name = "testfield", nullable = false)
	private String testfield;
	
	protected Testing() {}
	
	public Testing(String testfield) {
		this.testfield = testfield;
	}

}
