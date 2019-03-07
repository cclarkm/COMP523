package com.unc.hospitalschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "servceArea")
public class ServiceArea {

	@Id
	@Column (nullable = false)
	private int id;
	
	@Column (nullable = false)
	private String field;
}
