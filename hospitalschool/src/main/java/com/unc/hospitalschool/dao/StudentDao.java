package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.unc.hospitalschool.model.Student;
import com.unc.hospitalschool.model.Teacher;

@Repository
public interface StudentDao extends CrudRepository<Student, Integer> {

  List<Student> findAll();

  List<Student> findByFirstName(String firstname);

  List<Student> findByLastName(String lastname);

  List<Student> findByFirstNameAndLastName(String firstname, String lastname);

  Student findBySid(int sid);

  List<Student> findByCurrTeacher(Teacher currTeacher);

  List<Student> findBySecondTeacher(Teacher secondTeacher);

}
