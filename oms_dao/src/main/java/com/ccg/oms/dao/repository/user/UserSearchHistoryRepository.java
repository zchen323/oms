package com.ccg.oms.dao.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccg.oms.dao.entiry.user.UserSearchHistoryEntity;

public interface UserSearchHistoryRepository extends JpaRepository<UserSearchHistoryEntity, Integer>{
	List<UserSearchHistoryEntity> findByUserId(String userid);
}
