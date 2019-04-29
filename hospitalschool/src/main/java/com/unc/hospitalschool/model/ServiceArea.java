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
@Table(name = "serviceArea")
public class ServiceArea {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sid", nullable = false)
  private int sid;

  @Column(name = "field1", nullable = false)
  private String field1;

  protected ServiceArea() {}

  public ServiceArea(String field1) {
    this.field1 = field1;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("sid", Integer.toString(sid));
    map.put("field1", field1);

    return map;
  }

  @Override
  public String toString() {
    return "ServiceArea: " + field1;
  }

  public String getServiceArea() {
    return field1;
  }

  public void setField1(String field) {
    this.field1 = field;
  }
}
