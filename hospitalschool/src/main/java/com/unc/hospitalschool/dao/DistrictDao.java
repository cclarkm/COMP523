package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unc.hospitalschool.model.District;

@Repository
public interface DistrictDao extends CrudRepository<District, Long>{

	District findByDid(int did);
	List<District> findAll();
	
}
