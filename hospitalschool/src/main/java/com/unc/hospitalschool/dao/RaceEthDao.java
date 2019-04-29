package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.unc.hospitalschool.model.RaceEth;

@Repository
public interface RaceEthDao extends CrudRepository<RaceEth, Long> {

  RaceEth findByRid(int rid);

  List<RaceEth> findAll();
}
