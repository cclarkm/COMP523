package com.unc.hospitalschool.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "admissions")
public class Admission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "aid", nullable = false)
  private int aid;

  @ManyToOne
  @JoinColumn(name = "sid")
  @JsonProperty(value = "sid")
  private Student student;

  @Column(name = "admission", nullable = false)
  private String admissionDate;

  @Column(name = "discharge", nullable = true)
  private String dischargeDate;

  public Admission() {}

  public Admission(/* int aid, */Student student, String adminDate, String disDate) {
    // this.aid = aid;
    this.student = student;
    this.admissionDate = adminDate;
    this.dischargeDate = disDate;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("aid", Integer.toString(this.aid));
    map.put("student", this.student.getFullName());
    map.put("admissionDate", this.admissionDate);
    map.put("dischargeDate", this.dischargeDate);
    return map;
  }

  public void setAid(int aid) {
    this.aid = aid;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public void setAdmissionDate(String admissionDate) {
    this.admissionDate = admissionDate;
  }

  public void setDischargeDate(String dischargeDate) {
    this.dischargeDate = dischargeDate;
  }

  public String getAdmissionDate() {
    return this.admissionDate;
  }

  public int getId() {
    return aid;
  }

  @Override
  public String toString() {
    return "Admissions [aid=" + this.aid + ", student=" + this.student.getFullName()
        + ", admissionDate=" + this.admissionDate + ", dischargeDate=" + this.dischargeDate;
  }

  public static boolean validateFields(Map<String, String> body) {
    boolean student = body.containsKey("student");
    boolean admitDate = body.containsKey("admission");

    return student && admitDate;
  }
}
