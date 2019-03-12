package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unc.hospitalschool.model.PSLabel;

@Repository
public interface PSLabelDao extends CrudRepository<PSLabel, Long>{

	PSLabel findByLid(int lid);
	List<PSLabel> findAll();
	
}
