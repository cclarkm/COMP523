package com.unc.hospitalschool.dao;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.Teacher;
import com.unc.hospitalschool.model.VisitInformation;

public interface VisitInformationDao extends CrudRepository<VisitInformation, Integer>{

	
	VisitInformation findById(int tid);
}
