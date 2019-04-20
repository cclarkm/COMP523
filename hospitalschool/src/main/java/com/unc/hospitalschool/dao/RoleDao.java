package com.unc.hospitalschool.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.unc.hospitalschool.model.Role;

public interface RoleDao extends CrudRepository<Role, Long>{

	Role findByRid(int rid);
	List<Role> findAll();
}
