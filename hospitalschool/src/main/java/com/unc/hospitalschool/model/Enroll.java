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
@Table(name = "enroll")
public class Enroll {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "eid", nullable = false)
  private int eid;

  @Column(name = "enroll", nullable = true)
  private String enroll;


  public Enroll() {}

  public Enroll(String enroll) {
    this.enroll = enroll;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("eid", Integer.toString(this.eid));
    map.put("enroll", enroll);
    return map;
  }

  public void setEnroll(String enroll) {
    this.enroll = enroll;
  }

  public String getEnroll() {
    return this.enroll;
  }

}
