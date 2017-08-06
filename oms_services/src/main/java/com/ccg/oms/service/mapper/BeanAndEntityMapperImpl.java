package com.ccg.oms.service.mapper;

import com.ccg.oms.common.data.document.DocType;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
import com.ccg.oms.common.data.user.User;
import com.ccg.oms.dao.entiry.document.DocTypeEntity;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.ProjectTemplateEntity;
import com.ccg.oms.dao.entiry.project.ProjectUserRoleTypeEntity;
import com.ccg.oms.dao.entiry.project.TaskTemplateEntity;
import com.ccg.oms.dao.entiry.user.UserEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-01-22T12:00:56-0600",
    comments = "version: 1.0.0.Final, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class BeanAndEntityMapperImpl implements BeanAndEntityMapper {

    @Override
    public ProjectUserRoleType entityToBean(ProjectUserRoleTypeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProjectUserRoleType projectUserRoleType = new ProjectUserRoleType();

        projectUserRoleType.setRoletype( entity.getRoletype() );
        projectUserRoleType.setDescription( entity.getDescription() );
        projectUserRoleType.setCreatedTS( entity.getCreatedTS() );
        projectUserRoleType.setCreatedBy( entity.getCreatedBy() );
        projectUserRoleType.setStatus( entity.getStatus() );
        projectUserRoleType.setRoletypecol( entity.getRoletypecol() );

        return projectUserRoleType;
    }

    @Override
    public ProjectUserRoleTypeEntity beanToEntity(ProjectUserRoleType bean) {
        if ( bean == null ) {
            return null;
        }

        ProjectUserRoleTypeEntity projectUserRoleTypeEntity = new ProjectUserRoleTypeEntity();

        projectUserRoleTypeEntity.setRoletype( bean.getRoletype() );
        projectUserRoleTypeEntity.setDescription( bean.getDescription() );
        projectUserRoleTypeEntity.setCreatedTS( bean.getCreatedTS() );
        projectUserRoleTypeEntity.setCreatedBy( bean.getCreatedBy() );
        projectUserRoleTypeEntity.setStatus( bean.getStatus() );
        projectUserRoleTypeEntity.setRoletypecol( bean.getRoletypecol() );

        return projectUserRoleTypeEntity;
    }

    @Override
    public ProjectTemplate entityToBean(ProjectTemplateEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProjectTemplate projectTemplate = new ProjectTemplate();

        projectTemplate.setId( entity.getId() );
        projectTemplate.setName( entity.getName() );
        projectTemplate.setDescription( entity.getDescription() );
        projectTemplate.setStatus( entity.getStatus() );
        projectTemplate.setCreatedTS( entity.getCreatedTS() );
        projectTemplate.setCreatedBy( entity.getCreatedBy() );
        projectTemplate.setConfig( entity.getConfig() );

        return projectTemplate;
    }

    @Override
    public ProjectTemplateEntity beanToEntity(ProjectTemplate bean) {
        if ( bean == null ) {
            return null;
        }

        ProjectTemplateEntity projectTemplateEntity = new ProjectTemplateEntity();

        projectTemplateEntity.setId( bean.getId() );
        projectTemplateEntity.setName( bean.getName() );
        projectTemplateEntity.setDescription( bean.getDescription() );
        projectTemplateEntity.setStatus( bean.getStatus() );
        projectTemplateEntity.setCreatedTS( bean.getCreatedTS() );
        projectTemplateEntity.setCreatedBy( bean.getCreatedBy() );
        if(bean.getConfig()!=null&&bean.getConfig().length()>0)
        projectTemplateEntity.setConfig( bean.getConfig() );

        return projectTemplateEntity;
    }

    @Override
    public TaskTemplate entityToBean(TaskTemplateEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TaskTemplate taskTemplate = new TaskTemplate();

        taskTemplate.setId( entity.getId() );
        taskTemplate.setName( entity.getName() );
        taskTemplate.setDescription( entity.getDescription() );
        taskTemplate.setStatus( entity.getStatus() );
        taskTemplate.setCreatedTS( entity.getCreatedTS() );
        taskTemplate.setCreatedBy( entity.getCreatedBy() );
        
        taskTemplate.setConfig( entity.getConfig() );

        return taskTemplate;
    }

    @Override
    public TaskTemplateEntity beanToEntity(TaskTemplate bean) {
        if ( bean == null ) {
            return null;
        }

        TaskTemplateEntity taskTemplateEntity = new TaskTemplateEntity();

        taskTemplateEntity.setId( bean.getId() );
        taskTemplateEntity.setName( bean.getName() );
        taskTemplateEntity.setDescription( bean.getDescription() );
        taskTemplateEntity.setStatus( bean.getStatus() );
        taskTemplateEntity.setCreatedTS( bean.getCreatedTS() );
        taskTemplateEntity.setCreatedBy( bean.getCreatedBy() );
        if(bean.getConfig()!=null&&bean.getConfig().length()>0)
        taskTemplateEntity.setConfig( bean.getConfig() );

        return taskTemplateEntity;
    }

//    @Override
//    public Project entityToBean(ProjectEntity entity) {
//        if ( entity == null ) {
//            return null;
//        }
//
//        Project project = new Project();
//
//        project.setId( entity.getId() );
//        project.setName( entity.getName() );
//        project.setDescription( entity.getDescription() );
//
//        return project;
//    }

//    @Override
//    public ProjectEntity beanToEntity(Project bean) {
//        if ( bean == null ) {
//            return null;
//        }
//
//        ProjectEntity projectEntity = new ProjectEntity();
//
//        projectEntity.setId( bean.getId() );
//        projectEntity.setName( bean.getName() );
//        projectEntity.setDescription( bean.getDescription() );
//
//        return projectEntity;
//    }

    @Override
    public User entityToBean(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( entity.getUsername() );
        user.setEmail( entity.getEmail() );

        return user;
    }

    @Override
    public UserEntity beanToEntity(User bean) {
        if ( bean == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername( bean.getUsername() );
        userEntity.setEmail( bean.getEmail() );

        return userEntity;
    }

    @Override
    public DocType entityToBean(DocTypeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DocType docType = new DocType();

        docType.setDoctype( entity.getDoctype() );
        docType.setDescription( entity.getDescription() );
        docType.setSampleURL( entity.getSampleURL() );
        docType.setCreatedTS( entity.getCreatedTS() );
        docType.setCreatedBy( entity.getCreatedBy() );

        return docType;
    }

    @Override
    public DocTypeEntity beanToEntity(DocType bean) {
        if ( bean == null ) {
            return null;
        }

        DocTypeEntity docTypeEntity = new DocTypeEntity();

        docTypeEntity.setDoctype( bean.getDoctype() );
        docTypeEntity.setDescription( bean.getDescription() );
        docTypeEntity.setSampleURL( bean.getSampleURL() );
        docTypeEntity.setCreatedTS( bean.getCreatedTS() );
        docTypeEntity.setCreatedBy( bean.getCreatedBy() );

        return docTypeEntity;
    }
}
