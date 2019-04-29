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
@Table(name = "logType")
public class LogType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lid", nullable = false)
  private int lid;

  @Column(name = "LogType", nullable = false)
  private String logType;

  protected LogType() {}

  public LogType(String logType) {
    this.logType = logType;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("lid", Integer.toString(lid));
    map.put("logType", logType);

    return map;
  }

  @Override
  public String toString() {
    return "LogType: " + this.logType;
  }

  public String getLogType() {
    return this.logType;
  }

  public int getId() {
    return lid;
  }

  public void setLogType(String logType) {
    this.logType = logType;
  }
}
