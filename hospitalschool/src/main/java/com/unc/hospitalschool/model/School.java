package com.unc.hospitalschool.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "school")
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sid", nullable = false)
  private int sid;

  @Column(name = "schoolName", nullable = false)
  private String schoolName;

  protected School() {}

  public School(String schoolName) {
    this.schoolName = schoolName;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("sid", Integer.toString(sid));
    map.put("school", schoolName);

    return map;
  }

  @Override
  public String toString() {
    return "School: " + schoolName;
  }

  public String getSchool() {
    return schoolName;
  }

  public void setSchool(String school) {
    this.schoolName = school;
  }
}
