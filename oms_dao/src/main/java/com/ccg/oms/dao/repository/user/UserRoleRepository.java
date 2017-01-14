package com.ccg.oms.dao.repository.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.user.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer>{
	List<UserRole> findByUsername(String username);
	UserRole findByUsernameAndRole(String username, String role);
}
