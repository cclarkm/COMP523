package com.unc.hospitalschool.model;

import java.util.Date;
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
@Table(name = "blackListToken")
public class BlackListToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tid", nullable = false)
  private int tid;

  @Column(name = "token", nullable = false)
  private String token;

  @Column(name = "expiration", nullable = false)
  private Date expr;

  protected BlackListToken() {}

  public BlackListToken(String token, Date date) {
    this.token = token;
    this.expr = date;
  }

  public String getToken() {
    return this.token;
  }

  public Date getDate() {
    return this.expr;
  }

  public int getTid() {
    return this.tid;
  }
}
