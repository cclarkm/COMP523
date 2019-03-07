package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.Testing;

public interface TestDao extends CrudRepository<Testing, Long>{
	
	List<Testing> findAll();

}
