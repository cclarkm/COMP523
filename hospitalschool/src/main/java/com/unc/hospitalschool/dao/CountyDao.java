package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.unc.hospitalschool.model.County;

@Repository
public interface CountyDao extends CrudRepository<County, Long> {

  County findByCid(int cid);

  List<County> findAll();

}
