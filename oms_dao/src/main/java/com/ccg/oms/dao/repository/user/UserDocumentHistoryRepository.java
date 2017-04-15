package com.ccg.oms.dao.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccg.oms.dao.entiry.user.UserDocumentHistoryEntity;

public interface UserDocumentHistoryRepository extends JpaRepository<UserDocumentHistoryEntity, Integer>{
	List<UserDocumentHistoryEntity> findByUserIdOrderByIdDesc(String userid);
}
