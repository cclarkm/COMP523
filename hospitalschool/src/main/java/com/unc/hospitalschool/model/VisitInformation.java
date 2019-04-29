package com.unc.hospitalschool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;

@Entity
@Table(name = "visitInformation")
public class VisitInformation {

  @Id
  @Column(nullable = false, name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "sid")
  @JsonProperty(value = "sid")
  private Student student;

  @Column(nullable = false)
  @JsonProperty(value = "dov")
  private String dov;

  @Column(nullable = false)
  @JsonProperty(value = "notes")
  private String notes;

  @ManyToOne
  @JoinColumn(name = "tid")
  @JsonProperty(value = "tid")
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "lid")
  @JsonProperty(value = "lid")
  private LogType logType;

  @Column(nullable = false)
  @JsonProperty(value = "clinic")
  private boolean clinic;

  public VisitInformation() {}

  public VisitInformation(Student sid, String dov, String notes, Teacher tid, LogType lid,
      boolean clinic) {
    this.student = sid;
    this.dov = dov;
    this.notes = notes;
    this.teacher = tid;
    this.logType = lid;
    this.clinic = clinic;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public void setDov(String dov) {
    this.dov = dov;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public void setLogType(LogType logType) {
    this.logType = logType;
  }

  public void setClinic(boolean clinic) {
    this.clinic = clinic;
  }

  public int getId() {
    return id;
  }

  public String getNotes() {
    return notes;
  }

  public static boolean validateFields(Map<String, String> body) {
    boolean student = body.containsKey("sid");
    boolean dov = body.containsKey("dov");
    boolean notes = body.containsKey("notes");
    boolean teacher = body.containsKey("tid");
    boolean logType = body.containsKey("logType");
    boolean clinic = body.containsKey("clinic");
    return student && dov && notes && teacher && logType && clinic;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("id", Long.toString(this.id));
    map.put("student", this.student.getFullName());
    map.put("dov", this.dov);
    map.put("notes", this.notes);
    map.put("teacher", this.teacher.getFirstName() + " " + this.teacher.getLastName());
    map.put("logType", this.logType.getLogType());
    map.put("clinic", Boolean.toString(this.clinic));
    return map;
  }

  @Override
  public String toString() {
    return "Visit Information [id=" + id + ", student=" + this.student.getFullName() + ", dov="
        + this.dov + ", notes=" + this.notes + ", teacher " + this.teacher.getFirstName() + " "
        + this.teacher.getLastName() + ", logType=" + this.logType.getLogType() + ", clinic="
        + this.clinic;
  }
}
