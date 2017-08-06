package com.ccg.oms.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.service.ReportService;
import com.ccg.oms.service.mapper.ProjectMapper;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	ProjectRepository projectRepository;
	
	@Override
	public List<Project> getProjectSummary() {
		
		final PageRequest pageRequest = new PageRequest(
				0, 1000, Direction.DESC, "id");
		
		List<Project> projects = new ArrayList<Project>();
		Iterable<ProjectEntity> entities = projectRepository.findAll(pageRequest);
		
		for(ProjectEntity entity : entities){
			projects.add(ProjectMapper.fromEntity(entity));
		}
		return projects;
	}

}
