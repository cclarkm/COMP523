package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unc.hospitalschool.model.Gender;

@Repository
public interface GenderDao extends CrudRepository<Gender, Long>{
	
	Gender findById(int id);
	List<Gender> findAll();
}
