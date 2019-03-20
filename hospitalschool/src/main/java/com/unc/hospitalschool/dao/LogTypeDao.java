package com.unc.hospitalschool.dao;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.LogType;

public interface LogTypeDao extends CrudRepository<LogType, Integer>{

	LogType findByLid(int lid);
}
