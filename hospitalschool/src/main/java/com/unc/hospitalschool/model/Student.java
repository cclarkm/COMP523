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

	@Column(nullable = true)
	@JsonProperty(value = "clinic")
	private boolean clinic;

	@Column(nullable = false)
	@JsonProperty(value = "hispanic")
	private boolean hispanic;

	@Column(nullable = true)
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
	
	public Student(String lastName, String firstName, String dob, Gender gender, RaceEth raceEth,
			ServiceArea serviceArea, School school, District district, County county, Grade grade, boolean hispanic) {
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
		this.hispanic = hispanic;
	}
	
	public Student() {
		
	}
	
	public static boolean validateFields(Map<String, String> body) {
		boolean lName = body.containsKey("lastName");
		boolean fName = body.containsKey("firstName");
		boolean dob = body.containsKey("dob");
		boolean gender = body.containsKey("gender");
		boolean raceEth = body.containsKey("raceEthnicity");
		boolean servArea = body.containsKey("serviceArea");
		boolean school = body.containsKey("school");
		boolean district = body.containsKey("district");
		boolean county = body.containsKey("county");
		boolean grade = body.containsKey("grade");
		boolean hispanic = body.containsKey("hispanic");
		return lName && fName && dob && gender && raceEth && servArea && school && district && county && grade && hispanic;
	}

	
	public Map<String, String> toJson(){
		HashMap<String, String> map = new HashMap<>();
		map.put("id", Long.toString(sid));
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("dob", dob);
		if (gender != null) {
			map.put("gender", gender.getGender());
		}
		
		if (raceEth != null) {
			map.put("raceEthnicity", raceEth.getRaceEth());
		}
		
		if (serviceArea != null) {
			map.put("serviceArea", serviceArea.getServiceArea());
		}
		
		if (school != null) {
			map.put("school", school.getSchool());
		}
		
		if (district != null) {
			map.put("district", district.getSchoolDistrict());
		}
		
		if (county != null) {
			map.put("county", county.getCounty());
		}
		
		if (grade != null) {
			map.put("grade", grade.getGrade());
		}
		
		if (studentNotes !=null) {
			map.put("studentNotes", studentNotes);
		}
		
		if (permissionDate != null) {
			map.put("permissionDate", permissionDate);
		}
		
		if (label != null) {
			map.put("label", label);
		}
		
		if (psLabel != null) {
			map.put("psLabelCode", psLabel.getCode());
			map.put("psLabelLabel", psLabel.getLabel());
		}
		
		if (currTeacher != null) {
			map.put("currentTeacher", currTeacher.getFirstName() + " " + currTeacher.getLastName());
		}
		
		if (secondTeacher != null) {
			map.put("secondTeacher", secondTeacher.getFirstName() + " " + secondTeacher.getLastName());
		}
		
		
		map.put("clinic", Boolean.toString(clinic));
		map.put("hispanic", Boolean.toString(hispanic));
		map.put("petTherapy",  Boolean.toString(petTherapy));
		
		if (newYrMessage != null) {
			map.put("newYrMessage", newYrMessage);
		}
		return map;
		
	}
	
	public void setLastName(String lastName) throws Exception{
		checkValid("lastName", lastName);
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) throws Exception{
		checkValid("firstName", firstName);
		this.firstName = firstName;
	}

	public void setDob(String dob) throws Exception{
		checkValid("dob", dob);
		this.dob = dob;
	}

	public void setGender(Gender gender) throws Exception{
		checkValid("gender", gender);
		this.gender = gender;
	}

	public void setRaceEth(RaceEth raceEth) throws Exception{
		checkValid("raceEthnicity", raceEth);
		this.raceEth = raceEth;
	}

	public void setServiceArea(ServiceArea serviceArea) throws Exception{
		checkValid("serviceArea", serviceArea);
		this.serviceArea = serviceArea;
	}

	public void setSchool(School school) throws Exception{
		checkValid("school", school);
		this.school = school;
	}

	public void setDistrict(District district) throws Exception{
		checkValid("district", district);
		this.district = district;
	}

	public void setCounty(County county) throws Exception{
		checkValid("county", county);
		this.county = county;
	}

	public void setGrade(Grade grade) throws Exception{
		checkValid("grade", grade);
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

	public void setPsLabel(PSLabel psLabel){
		this.psLabel = psLabel;
	}

	public void setCurrTeacher(Teacher currTeacher){
		this.currTeacher = currTeacher;
	}

	public void setSecondTeacher(Teacher secondTeacher){
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
	
	public String getDob() {
		return this.dob;
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public RaceEth getRaceEth() {
		return this.raceEth;
	}
	
	public ServiceArea getServiceArea() {
		return this.serviceArea;
	}
	
	public District getDistrict() {
		return this.district;
	}
	
	public County getCounty() {
		return this.county;
	}
	
	public Grade getGrade() {
		return this.grade;
	}
	
	public boolean getHispanic() {
		return this.hispanic;
	}
	
	public School getSchool() {
		return this.school;
	}
	
	
	
	public void setId(int id) {
		this.sid = id;
	}
	
	public int getSid() {
		return this.sid;
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
	
	private void checkValid(String fieldName, Object field) throws Exception{
		if (field == null) {
			throw new Exception("No matching entity for field: " + fieldName);
		}
	}
}
