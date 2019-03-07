package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

	List<Student> findAll();
}
