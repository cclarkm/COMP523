package com.unc.hospitalschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "raceEth")
public class RaceEth {

	@Id
	@Column(nullable = false)
	private int id;
	
	@Column(nullable = false)
	private String raceEth;
	
	@Column(nullable = false)
	private String code;
}