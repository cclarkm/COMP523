package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.School;

public interface SchoolDao extends CrudRepository<School, Long>{
	
	List<School> findAll();

}
