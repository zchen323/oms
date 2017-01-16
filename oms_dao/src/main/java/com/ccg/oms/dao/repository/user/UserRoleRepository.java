package com.ccg.oms.dao.repository.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.user.UserRoleEntity;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Integer>{
	List<UserRoleEntity> findByUsername(String username);
	UserRoleEntity findByUsernameAndRole(String username, String role);
}
