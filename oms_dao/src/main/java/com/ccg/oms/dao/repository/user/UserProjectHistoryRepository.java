package com.ccg.oms.dao.repository.user;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ccg.oms.dao.entiry.user.UserProjectHistoryEntity;

public interface UserProjectHistoryRepository extends JpaRepository<UserProjectHistoryEntity, Integer> {
	List<UserProjectHistoryEntity> findByUserIdOrderByIdDesc(String userid);//, PageRequest pageRequest);
	List<UserProjectHistoryEntity> findByProjectId(Integer projectId);
	
}
