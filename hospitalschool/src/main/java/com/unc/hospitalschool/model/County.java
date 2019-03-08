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
@Table(name = "county")
public class County {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "cid", nullable = false)
	private Long cid;
	
	@Column(name = "county", nullable = false)
	private String county;
	
	protected County() {}
	
	public County(String county) {
		this.county = county;
	}
	
	@Override
	public String toString() {
		return "County: " + county;
	}

}
