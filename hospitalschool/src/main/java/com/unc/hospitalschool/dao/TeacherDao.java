package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unc.hospitalschool.model.Teacher;

@Repository
public interface TeacherDao extends CrudRepository<Teacher, Long>{

	Teacher findByTid(int tid);
	List<Teacher> findAll();
	
}
