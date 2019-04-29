package com.unc.hospitalschool.dao;

import java.util.List;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.unc.hospitalschool.model.LogType;
import com.unc.hospitalschool.model.Student;
import com.unc.hospitalschool.model.Teacher;
import com.unc.hospitalschool.model.VisitInformation;

public interface VisitInformationDao extends PagingAndSortingRepository<VisitInformation, Integer> {


  VisitInformation findById(int tid);

  List<VisitInformation> findByStudentOrderByDovDesc(Student student);

  List<VisitInformation> findByStudentAndLogTypeOrderByDovDesc(Student student, LogType logType);

  List<VisitInformation> findByStudentAndTeacherOrderByDovDesc(Student student, Teacher teacher);


  List<VisitInformation> findByStudentAndTeacherAndLogTypeOrderByDovDesc(Student student,
      Teacher teacher, LogType logType);



  Page<VisitInformation> findByStudentOrderByDovDesc(Student student, Pageable pageable);

  Page<VisitInformation> findByStudentAndLogTypeOrderByDovDesc(Student student, LogType logType,
      Pageable pageable);

  Page<VisitInformation> findByStudentAndTeacherOrderByDovDesc(Student student, Teacher teacher,
      Pageable pageable);


  Page<VisitInformation> findByStudentAndTeacherAndLogTypeOrderByDovDesc(Student student,
      Teacher teacher, LogType logType, Pageable pageable);


}
