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
@Table(name = "county")
public class County {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cid", nullable = false)
  private int cid;

  @Column(name = "county", nullable = false)
  private String county;

  protected County() {}

  public County(String county) {
    this.county = county;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("cid", Integer.toString(cid));
    map.put("county", county);

    return map;
  }

  @Override
  public String toString() {
    return "County: " + county;
  }

  public String getCounty() {
    return this.county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

}
