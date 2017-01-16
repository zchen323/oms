package com.ccg.oms.dao.repository.user;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.user.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String>{

}
