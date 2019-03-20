package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.LogType;
import com.unc.hospitalschool.model.Student;
import com.unc.hospitalschool.model.Teacher;
import com.unc.hospitalschool.model.VisitInformation;

public interface VisitInformationDao extends CrudRepository<VisitInformation, Integer>{

	
	VisitInformation findById(int tid);
	
	List<VisitInformation> findByStudentOrderByDovDesc(Student student);
	
	List<VisitInformation> findByStudentAndLogTypeOrderByDovDesc(Student student, LogType logType);
	List<VisitInformation> findByStudentAndTeacherOrderByDovDesc(Student student, Teacher teacher);
	
	
	List<VisitInformation> findByStudentAndTeacherAndLogTypeOrderByDovDesc(Student student, Teacher teacher, LogType logType);
	
}
