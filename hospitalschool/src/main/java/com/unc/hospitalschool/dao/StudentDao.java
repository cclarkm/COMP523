package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.unc.hospitalschool.model.Student;

@Repository
<<<<<<< HEAD
public interface StudentDao extends CrudRepository<Student, Integer> {

  List<Student> findAll();

  List<Student> findByFirstName(String firstname);

  List<Student> findByLastName(String lastname);

  List<Student> findByFirstNameAndLastName(String firstname, String lastname);

  Student findBySid(int sid);

  Student save(Student student);
=======
public interface StudentDao extends CrudRepository<Student, Integer>{

	List<Student> findAll();
	List<Student> findByFirstName(String firstname);
	List<Student> findByLastName(String lastname);
	List<Student> findByFirstNameAndLastName(String firstname, String lastname);
	Student findBySid(int sid);
	Student save(Student student);
	List<Student> findByPrimaryTid(int currTeacher);
	List<Student> findBySecondaryTid(int secondTeacher);
>>>>>>> c48922aeda56cdd8d463d2a8995a2d3cedad3a26

}
