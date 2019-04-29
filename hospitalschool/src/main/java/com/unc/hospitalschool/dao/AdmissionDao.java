package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.unc.hospitalschool.model.Admission;
import com.unc.hospitalschool.model.Student;

public interface AdmissionDao extends PagingAndSortingRepository<Admission, Long> {

  Admission findByAid(int aid);


  List<Admission> findByStudent(Student student);



  Page<Admission> findByStudent(Student student, Pageable pageable);

}
