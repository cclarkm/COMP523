package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unc.hospitalschool.model.Student;

@Repository
public interface StudentDao extends CrudRepository<Student, Long>{

	List<Student> findAll();
}
