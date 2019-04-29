package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.unc.hospitalschool.model.LogType;

public interface LogTypeDao extends CrudRepository<LogType, Integer> {

  LogType findByLid(int lid);

  List<LogType> findAll();
}
