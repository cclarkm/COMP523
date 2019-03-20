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

@Entity
@Table(name = "gender")
public class Gender {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "gid", nullable = false)
	private int id;
	
	@Column(name = "gender", nullable = false)
	private String gender;
	
	protected Gender() {}
	
	public Gender(String gender) {
		this.gender = gender;
	}
	
	public Map<String, String> toJson(){
		HashMap<String, String> map = new HashMap<>();
		map.put("gid", Integer.toString(id));
		map.put("gender", gender);
		
		return map;
	}
	
	@Override
	public String toString() {
		return "Gender: " + gender;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
