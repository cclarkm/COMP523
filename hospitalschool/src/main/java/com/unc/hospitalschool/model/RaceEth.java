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
@Table(name = "raceEth")
public class RaceEth {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "rid", nullable = false)
	private int rid;
	
	@Column(name = "raceEth", nullable = false)
	private String raceEth;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	protected RaceEth() {}
	
	public RaceEth(String raceEth, String code) {
		this.raceEth = raceEth;
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "Code: " + code + ", race/ethnicity: " + raceEth;
	}
	
	public String getRaceEth() {
		return raceEth;
	}
}