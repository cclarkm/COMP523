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
@Table(name = "label")
public class PSLabel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lid", nullable = false)
  private int lid;

  @Column(name = "label", nullable = false)
  private String label;

  @Column(name = "code", nullable = false)
  private String code;

  protected PSLabel() {}

  public PSLabel(String label, String code) {
    this.label = label;
    this.code = code;
  }

  public Map<String, String> toJson() {
    HashMap<String, String> map = new HashMap<>();
    map.put("lid", Integer.toString(lid));
    map.put("label", label);
    map.put("code", code);

    return map;
  }

  @Override
  public String toString() {
    return "Code: " + code + ", label: " + label;
  }

  public String getLabel() {
    return label;
  }

  public String getCode() {
    return code;
  }

  public void setLabel(String psLabel) {
    this.label = psLabel;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
