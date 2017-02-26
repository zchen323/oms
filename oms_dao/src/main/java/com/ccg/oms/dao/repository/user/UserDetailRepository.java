package com.ccg.oms.dao.repository.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.user.UserDetailEntity;

public interface UserDetailRepository extends CrudRepository<UserDetailEntity, String>{
	List<UserDetailEntity> findByNameContainingOrderByName(String nameContains);
}
