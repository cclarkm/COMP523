package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.unc.hospitalschool.model.ServiceArea;

@Repository
public interface ServiceAreaDao extends CrudRepository<ServiceArea, Long> {

  ServiceArea findBySid(int sid);

  List<ServiceArea> findAll();
}
