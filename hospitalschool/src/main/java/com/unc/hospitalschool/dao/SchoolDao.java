package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unc.hospitalschool.model.School;

@Repository
public interface SchoolDao extends CrudRepository<School, Integer>{
	
	School findBySid(int sid);
	List<School> findAll();

}
