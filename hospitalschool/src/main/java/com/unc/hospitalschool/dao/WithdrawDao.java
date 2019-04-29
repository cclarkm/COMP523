package com.unc.hospitalschool.dao;

import org.springframework.data.repository.CrudRepository;
import com.unc.hospitalschool.model.Withdraw;

public interface WithdrawDao extends CrudRepository<Withdraw, Long> {

  Withdraw findByWid(int wid);
}
