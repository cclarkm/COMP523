package com.unc.hospitalschool.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.Teacher;
import com.unc.hospitalschool.model.VisitInformation;

public interface VisitInformationDao extends CrudRepository<VisitInformation, Integer>{

	
	VisitInformation findById(int tid);
	
	List<VisitInformation> findByStudentOrderByDovDesc(int sid);
	
	List<VisitInformation> findByStudentAndLogTypeOrderByDovDesc(int sid, int lid);
	List<VisitInformation> findByStudentAndTeacherOrderByDovDesc(int sid, int tid);
	
	
	List<VisitInformation> findByStudentAndTeacherAndLogTypeOrderByDovDesc(int sid, int tid, int lid);
	
}
