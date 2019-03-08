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
@Table(name = "label")
public class PSLabel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "lid", nullable = false)
	private Long lid;
	
	@Column(name = "label", nullable = false)
	private String label;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	protected PSLabel() {}
	
	public PSLabel(String label, String code) {
		this.label = label;
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "Code: " + code + ", label: " + label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getCode() {
		return code;
	}
}
