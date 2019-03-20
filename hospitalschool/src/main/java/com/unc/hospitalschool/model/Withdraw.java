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
@Table(name="withdraw")
public class Withdraw {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "wid", nullable = false)
	private int wid;
	
	@Column(name = "withdraw", nullable = false)
	private String withdraw;
	
	protected Withdraw() {}
	
	public Withdraw(String withdraw) {
		this.withdraw = withdraw;
	}
	
	public Map<String, String> toJson(){
		HashMap<String, String> map = new HashMap<>();
		map.put("wid", Integer.toString(wid));
		map.put("withdraw", withdraw);
		
		return map;
	}
	
	@Override
	public String toString() {
		return "Withdraw: " + this.withdraw;
	}
	
	public String getWithdraw() {
		return this.withdraw;
	}
}
