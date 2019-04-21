package com.unc.hospitalschool.dao;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.Enroll;

public interface EnrollDao extends CrudRepository<Enroll, Long>{

	Enroll findByEid(int eid);
}
