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
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "raceEth")
public class RaceEth {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rid", nullable = false)
  private int rid;

  @Column(name = "raceEth", nullable = false)
  private String raceEth;

  @Column(name = "code", nullable = false)
  @JsonProperty(value = "code")
  private String code;

  protected RaceEth() {}

  public RaceEth(String raceEth, String code) {
    this.raceEth = raceEth;
    this.code = code;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("rid", Integer.toString(rid));
    map.put("raceEth", raceEth);
    map.put("code", code);

    return map;
  }

  @Override
  public String toString() {
    return "Code: " + code + ", race/ethnicity: " + raceEth;
  }

  public String getRaceEth() {
    return raceEth;
  }

  public String getCode() {
    return code;
  }

  public void setRaceEth(String raceEth) {
    this.raceEth = raceEth;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
