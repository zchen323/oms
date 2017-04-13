package com.ccg.oms.dao.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccg.oms.dao.entiry.user.UserDocumentHistoryEntity;
import com.ccg.oms.dao.entiry.user.UserProjectHistoryEntity;

public interface UserDocumentHistoryRepository extends JpaRepository<UserDocumentHistoryEntity, Integer>{
	List<UserDocumentHistoryEntity> findByUserId(String userid);
}
