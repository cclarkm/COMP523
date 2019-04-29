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
@Table(name = "district")
public class District {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "did", nullable = false)
  private int did;

  @Column(name = "schoolDistrict", nullable = false)
  private String schoolDistrict;

  protected District() {}

  public District(String schoolDistrict) {
    this.schoolDistrict = schoolDistrict;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("did", Integer.toString(did));
    map.put("district", schoolDistrict);

    return map;
  }

  @Override
  public String toString() {
    return "District: " + schoolDistrict;
  }

  public String getSchoolDistrict() {
    return schoolDistrict;
  }

  public void setDistrict(String district) {
    this.schoolDistrict = district;
  }
}
