package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unc.hospitalschool.model.Student;

@Repository
public interface StudentDao extends CrudRepository<Student, Integer>{

	List<Student> findAll();
	List<Student> findByFirstName(String firstname);
	List<Student> findByLastName(String lastname);
	List<Student> findByFirstNameAndLastName(String firstname, String lastname);
	Student findBySid(int sid);
	Student save(Student student);
	List<Student> findByPrimaryTid(int currTeacher);
	List<Student> findBySecondaryTid(int secondTeacher);

}
