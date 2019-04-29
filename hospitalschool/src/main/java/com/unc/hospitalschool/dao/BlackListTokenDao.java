package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.unc.hospitalschool.model.BlackListToken;

@Repository
public interface BlackListTokenDao extends CrudRepository<BlackListToken, Long> {

  BlackListToken findByTid(int tid);

  BlackListToken findByToken(String token);

  List<BlackListToken> findAll();
}
