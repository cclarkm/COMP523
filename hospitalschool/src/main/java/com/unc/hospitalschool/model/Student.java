package com.unc.hospitalschool.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(nullable = false, name="sid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sid;

	@Column(nullable = false)
	@JsonProperty(value = "lastName")
	private String lastName;

	@Column(nullable = false)
	@JsonProperty(value = "firstName")
	private String firstName;

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd") // could change - its currently different on access
	@JsonProperty(value = "dob")
	private String dob;

	@ManyToOne
	@JoinColumn(name = "gender")
	@JsonProperty(value = "gender")
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "raceEth")
	@JsonProperty(value = "raceEthnicity")
	private RaceEth raceEth;

	@ManyToOne
	@JoinColumn(name = "serviceArea")
	@JsonProperty(value = "serviceArea")
	private ServiceArea serviceArea;

	
	@ManyToOne
	@JoinColumn(name = "school")
	@JsonProperty(value = "school")
	private School school;

	@ManyToOne
	@JoinColumn(name = "district")
	@JsonProperty(value = "district")
	private District district;

	@ManyToOne
	@JoinColumn(name = "county")
	@JsonProperty(value = "county")
//	@Column(nullable = true)
	private County county;

	@ManyToOne
	@JoinColumn(name = "grade")
	@JsonProperty(value = "grade")
	private Grade grade;

	@Column(nullable = false)
	@JsonProperty(value = "studentNotes")
	private String studentNotes;

	@Column(nullable = false)
	@JsonProperty(value = "permissionDate")
	private String permissionDate;

	@Column(nullable = true)
	@JsonProperty(value = "label")
	private String label;

	@ManyToOne
	@JoinColumn(name = "psLabel")
	@JsonProperty(value = "psLabel")
	private PSLabel psLabel;

	@ManyToOne
	@JoinColumn(name = "currTeacher")
	@JsonProperty(value = "currentTeacher")
	private Teacher currTeacher;

	@ManyToOne
	@JoinColumn(name = "secondTeacher")
	@JsonProperty(value = "secondTeacher")
	private Teacher secondTeacher;

	@Column(nullable = false)
	@JsonProperty(value = "clinic")
	private boolean clinic;

	@Column(nullable = false)
	@JsonProperty(value = "hispanic")
	private boolean hispanic;

	@Column(nullable = false)
	@JsonProperty(value = "petTherapy")
	private boolean petTherapy;

	@Column(nullable = true)
	@JsonProperty(value = "newYrMessage")
	private String newYrMessage;

	public Student(String lastName, String firstName, String dob, Gender gender, RaceEth raceEth,
			ServiceArea serviceArea, School school, District district, County county, Grade grade, String studentNotes,
			String permissionDate, String label, PSLabel psLabel, Teacher currTeacher, Teacher secondTeacher,
			boolean clinic, boolean hispanic, boolean petTherapy, String newYrMessage) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.dob = dob;
		this.gender = gender;
		this.raceEth = raceEth;
		this.serviceArea = serviceArea;
		this.school = school;
		this.district = district;
		this.county = county;
		this.grade = grade;
		this.studentNotes = studentNotes;
		this.permissionDate = permissionDate;
		this.label = label;
		this.psLabel = psLabel;
		this.currTeacher = currTeacher;
		this.secondTeacher = secondTeacher;
		this.clinic = clinic;
		this.hispanic = hispanic;
		this.petTherapy = petTherapy;
		this.newYrMessage = newYrMessage;
	}
	
	public Student() {
		
	}

	
	public Map<String, String> toJson(){
		HashMap<String, String> map = new HashMap<>();
		map.put("id", Long.toString(sid));
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("dob", dob);
		map.put("gender", gender.getGender());
		map.put("raceEthnicity", raceEth.getRaceEth());
		map.put("serviceArea", serviceArea.getServiceArea());
		map.put("school", school.getSchool());
		map.put("district", district.getSchoolDistrict());
		map.put("county", county.getCounty());
		map.put("grade", grade.getGrade());
		map.put("studentNotes", studentNotes);
		map.put("permissionDate", permissionDate);
		map.put("label", label);
		map.put("psLabelCode", psLabel.getCode());
		map.put("psLabelLabel", psLabel.getLabel());
		map.put("currentTeacher", currTeacher.getFirstName() + " " + currTeacher.getLastName());
		map.put("secondTeacher", secondTeacher.getFirstName() + " " + secondTeacher.getLastName());
		map.put("clinic", Boolean.toString(clinic));
		map.put("hispanic", Boolean.toString(hispanic));
		map.put("petTherapy",  Boolean.toString(petTherapy));
		map.put("newYrMessage", newYrMessage);
		return map;
		
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setRaceEth(RaceEth raceEth) {
		this.raceEth = raceEth;
	}

	public void setServiceArea(ServiceArea serviceArea) {
		this.serviceArea = serviceArea;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}

	public void setPermissionDate(String permissionDate) {
		this.permissionDate = permissionDate;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setPsLabel(PSLabel psLabel) {
		this.psLabel = psLabel;
	}

	public void setCurrTeacher(Teacher currTeacher) {
		this.currTeacher = currTeacher;
	}

	public void setSecondTeacher(Teacher secondTeacher) {
		this.secondTeacher = secondTeacher;
	}

	public void setClinic(boolean clinic) {
		this.clinic = clinic;
	}

	public void setHispanic(boolean hispanic) {
		this.hispanic = hispanic;
	}

	public void setPetTherapy(boolean petTherapy) {
		this.petTherapy = petTherapy;
	}

	public void setNewYrMessage(String newYrMessage) {
		this.newYrMessage = newYrMessage;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", lastName=" + lastName + ", firstName=" + firstName + ", dob=" + dob
				+ ", gender=" + gender + ", raceEth=" + raceEth + ", serviceArea=" + serviceArea + ", school=" + school
				+ ", district=" + district + ", county=" + county + ", grade=" + grade + ", studentNotes="
				+ studentNotes + ", permissionDate=" + permissionDate + ", label=" + label + ", psLabel=" + psLabel
				+ ", currTeacher=" + currTeacher + ", secondTeacher=" + secondTeacher + ", clinic=" + clinic
				+ ", hispanic=" + hispanic + ", petTherapy=" + petTherapy + ", newYrMessage=" + newYrMessage + "]";
	}
}
