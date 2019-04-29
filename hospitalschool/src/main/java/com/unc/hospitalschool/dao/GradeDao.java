package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.unc.hospitalschool.model.Grade;

@Repository
public interface GradeDao extends CrudRepository<Grade, Long> {

  Grade findByGid(int gid);

  List<Grade> findAll();
}
