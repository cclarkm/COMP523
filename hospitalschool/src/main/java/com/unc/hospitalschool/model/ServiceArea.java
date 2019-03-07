package com.unc.hospitalschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.springframework.data.annotation.Id;

@Entity
@Table(name = "servceArea")
public class ServiceArea {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name = "sid", nullable = false)
	private Long sid;
	
	@Column (name = "field1", nullable = false)
	private String field1;
	
	protected ServiceArea() {}
	
	public ServiceArea(String field1) {
		this.field1 = field1;
	}
	
	@Override
	public String toString() {
		return "ServiceArea: " + field1;
	}
}
