package com.ccg.oms.dao.repository.user;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.user.User;

public interface UserRepository extends CrudRepository<User, String>{

}
