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
@Table(name="logType")
public class LogType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "lid", nullable = false)
	private int lid;
	
	@Column(name = "LogType", nullable = false)
	private String logType;
	
	protected LogType() {}
	
	public LogType(String logType) {
		this.logType = logType;
	}
	
	@Override
	public String toString() {
		return "LogType: " + this.logType;
	}
	
	public String getLogType() {
		return this.logType;
	}
}
