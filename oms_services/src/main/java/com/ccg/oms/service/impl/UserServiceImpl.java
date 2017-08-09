package com.ccg.oms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.user.NewUser;
import com.ccg.oms.common.data.user.User;
import com.ccg.oms.common.data.user.UserInfo;
import com.ccg.oms.common.data.user.UserWithPassword;
import com.ccg.oms.dao.entiry.document.DocumentEntity;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.user.UserDetailEntity;
import com.ccg.oms.dao.entiry.user.UserDocumentHistoryEntity;
import com.ccg.oms.dao.entiry.user.UserEntity;
import com.ccg.oms.dao.entiry.user.UserEntity2;
import com.ccg.oms.dao.entiry.user.UserProjectHistoryEntity;
import com.ccg.oms.dao.entiry.user.UserRoleEntity;
import com.ccg.oms.dao.entiry.user.UserRoleEntity2;
import com.ccg.oms.dao.entiry.user.UserSearchHistoryEntity;
import com.ccg.oms.dao.repository.document.DocumentRepository;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.dao.repository.user.User2Repository;
import com.ccg.oms.dao.repository.user.UserDetailRepository;
import com.ccg.oms.dao.repository.user.UserDocumentHistoryRepository;
import com.ccg.oms.dao.repository.user.UserProjectHistoryRepository;
import com.ccg.oms.dao.repository.user.UserRepository;
import com.ccg.oms.dao.repository.user.UserRoleRepository;
import com.ccg.oms.dao.repository.user.UserSearchHistoryRepository;
import com.ccg.oms.service.UserServices;
import com.ccg.oms.service.mapper.DocumentMapper;
import com.ccg.oms.service.mapper.ProjectMapper;
import com.ccg.oms.service.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserServices{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	User2Repository user2Repository;	
	
	@Autowired
	UserRoleRepository roleRepository;
	
	@Autowired
	UserDetailRepository detailRepository;
	
	@Autowired
	UserProjectHistoryRepository userProjectHistoryRepository;
	
	@Autowired
	UserDocumentHistoryRepository userDocumentHistoryRepository;
	
	@Autowired
	UserSearchHistoryRepository userSearchHistoryRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	DocumentRepository documentRepository;

	@Transactional
	public User findUserById(String username) {
		UserEntity userEntity = userRepository.findOne(username);
		if(!userEntity.getEnabled()){
			return null;
		}
		User user = this.mapToUser(userEntity);
		List<UserRoleEntity> roles = roleRepository.findByUsername(username);
		for(UserRoleEntity role : roles){
			user.getRoles().add(role.getRole());
		}
		return user;
	}
	
	@Override
	@Transactional
	public List<User> getUsers() {
		
		List<User> users = new ArrayList<User>();
		
		Iterable<UserEntity2> entities = user2Repository.findAll();
		for(UserEntity2 entity : entities){
			if(entity.getEnabled()){
				User user = new User();
				user.setEmail(entity.getEmail());
				user.setUsername(entity.getUsername());				
				Set<UserRoleEntity2> roles = entity.getRoles();
				for(UserRoleEntity2 role :roles){
					String r = role.getRole();
//					if(r.startsWith("ROLE_")){
//						r = r.substring(5);
//					}
					user.getRoles().add(r);
				}
				users.add(user);
			}
		}
		return users;
	}

	public void createUser(UserWithPassword user) {
		UserEntity userEntity = this.mapToUserEntity(user);
		userEntity.setPassword(user.getPassword());
		userRepository.save(userEntity);
		
		Set<String> roles = user.getRoles();
		if(roles != null){
			for(String role : roles){
				UserRoleEntity userRole = new UserRoleEntity();
				userRole.setRole(role);
				userRole.setUsername(user.getUsername());
				roleRepository.save(userRole);
			}
		}
	}

	public void removeUser(String username) {
		UserEntity userEntity = userRepository.findOne(username);
		userEntity.setEnabled(false);
		userRepository.save(userEntity);
	}

	public void createUserWithRoles(User user, String password) {
		UserEntity userEntity = this.mapToUserEntity(user);
		userEntity.setPassword(password);
		userRepository.save(userEntity);		
	}

	@Transactional
	public void assignToRole(String username, String role) {
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setUsername(username);
		userRole.setRole(role);
		roleRepository.save(userRole);
	}
	
	@Transactional
	public void removeFromRole(String username, String role){
		UserRoleEntity userRole  = roleRepository.findByUsernameAndRole(username, role);
		if(userRole != null){
			roleRepository.delete(userRole);
		}
	}
	
	@Transactional
	public void changePassword(String username, String oldPassword, String newPassword){
		com.ccg.oms.dao.entiry.user.UserEntity user = userRepository.findOne(username);
		if(user.getPassword().equals(oldPassword)){
			user.setPassword(newPassword);
		}
		userRepository.save(user);
	}
	
	public void updateEmail(String username, String newEmail) {
		com.ccg.oms.dao.entiry.user.UserEntity user = userRepository.findOne(username);
		user.setEmail(newEmail);
		userRepository.save(user);
	}	
	private User mapToUser(com.ccg.oms.dao.entiry.user.UserEntity userEntity){
		User user = new User();
		user.setUsername(userEntity.getUsername());
		user.setEmail(userEntity.getEmail());
		return user;
	}
	
	private com.ccg.oms.dao.entiry.user.UserEntity mapToUserEntity(User user){
		com.ccg.oms.dao.entiry.user.UserEntity userEntity = new com.ccg.oms.dao.entiry.user.UserEntity();
		userEntity.setEmail(user.getEmail());
		userEntity.setUsername(user.getUsername());
		return userEntity;
	}

	@Override
	@Transactional
	public void createNewUser(NewUser newUser) {
		
		if(newUser != null){
			String[] pass = newUser.getPass();
			if(pass != null && pass.length < 2 ||
					!pass[0].equals(pass[1])){
				
				System.out.println(pass[0] + ",  " + pass[1]);	
				throw new RuntimeException("password and verify does not match!");
			}
		}else{
			throw new RuntimeException("new user is empty");
		}
		
		UserEntity2 entity = user2Repository.findOne(newUser.getUsername());
		if(entity != null && entity.getEnabled()){
				if(!entity.getPassword().equals(newUser.getPass()[0])){
					throw new RuntimeException("User: " + newUser.getUsername() + " already exists");
				}
		}
		
		if(entity != null){
			entity.setEnabled(true);
		}else{
			entity = new UserEntity2();
		}
		
		// update user and user_role talbe
		entity.setEmail(newUser.getEmail());
		entity.setUsername(newUser.getUsername());
		entity.setPassword(newUser.getPass()[0]);
		
		Set<UserRoleEntity2> roleSet = new HashSet<UserRoleEntity2>();
		for(String role : newUser.getRoleSet()){
			UserRoleEntity2 roleEntity = new UserRoleEntity2();
//			if(!role.startsWith("ROLE_")){
//				role = "ROLE_" + role;
//			}
			roleEntity.setRole(role);
			roleEntity.setUser(entity);
			roleSet.add(roleEntity);
		}

		entity.setRoles(roleSet);
		user2Repository.save(entity);
		
		// update detail table
		UserDetailEntity detailEntity = detailRepository.findOne(newUser.getUsername());
		if(detailEntity == null){
			detailEntity = new UserDetailEntity();
		}
		detailEntity.setUsername(newUser.getUsername());
		detailEntity.setName(newUser.getName());
		detailEntity.setCompany(newUser.getCompany());
		detailEntity.setFullAccess(newUser.getFullaccess());
		detailEntity.setIsContractor(newUser.getIscontractor());
		detailEntity.setEmail(newUser.getEmail());
		detailEntity.setCreatedTS(new Timestamp(System.currentTimeMillis()));
		
		detailRepository.save(detailEntity);
	}

	@Override
	@Transactional
	public List<UserInfo> getUserInfo() {		
		Iterable<UserEntity2> userEntities = user2Repository.findAll();
		Map<String, UserInfo> map = new HashMap<String, UserInfo>();
		for(UserEntity2 userEntity : userEntities){
			if(userEntity.getEnabled() && !userEntity.getUsername().trim().isEmpty()){
				UserInfo info = new UserInfo();
				info.setUsername(userEntity.getUsername());
				info.setEmail(userEntity.getEmail());
				StringBuffer sb = new StringBuffer();
				Set<UserRoleEntity2> roles = userEntity.getRoles();
				for(UserRoleEntity2 role : roles){
					String r = role.getRole();
//					if(r.startsWith("ROLE_")){
//						r = r.substring(5);
//					}
					sb.append(r).append(", ");
				}
				String temp = sb.toString();
				if(temp.length() > 2){
					temp = temp.substring(0, temp.length() - 2);
				}
				info.setRole(temp);
				map.put(info.getUsername(), info);
			}
		}
		
		// get user detail
		Iterable<UserDetailEntity> detailEntities = detailRepository.findAll();
		for(UserDetailEntity detail : detailEntities){
			String username = detail.getUsername();
			UserInfo info = map.get(username);
			if(info != null){
				info.setFullaccess(detail.getFullAccess());
				info.setCreatedDate(detail.getCreatedTS());
				info.setName(detail.getName());
			}
		}
		List<UserInfo> list = new ArrayList<UserInfo>();
		Set<String> keySet = map.keySet();
		for(String key : keySet){
			list.add(map.get(key));
		}
		
		return list;
	}
	
	@Override
	@Transactional
	public void updateUser(NewUser newUser) {
		UserEntity2 entity = user2Repository.findOne(newUser.getUsername());
		if(entity == null){
			throw new RuntimeException("User: " + newUser.getUsername() + " not found");
		}
		
		// update user and user_role talbe
		entity.setEmail(newUser.getEmail());
		user2Repository.save(entity);
		
		// update detail table
		UserDetailEntity detailEntity = detailRepository.findOne(newUser.getUsername());
		if(detailEntity == null){
			detailEntity = new UserDetailEntity();
		}
		detailEntity.setUsername(newUser.getUsername());
		detailEntity.setName(newUser.getName());
		detailEntity.setCompany(newUser.getCompany());
		detailEntity.setFullAccess(newUser.getFullaccess());
		detailEntity.setIsContractor(newUser.getIscontractor());
		detailEntity.setEmail(newUser.getEmail());		
		detailRepository.save(detailEntity);
		
		// update use roles, get the roles first
		Set<UserRoleEntity2>  roleEntityset = entity.getRoles();
		Set<String> existingRoles = new HashSet<String>();
		for(UserRoleEntity2 roleEntity : roleEntityset){
			existingRoles.add(roleEntity.getRole());
		}
		
		// remove deleted
		for(String role : existingRoles){
			if(!newUser.getRoleSet().contains(role)){
				// remove form db
				roleRepository.deleteByUsernameAndRole(entity.getUsername(), role);
			}
		}
		
		// add new
		for(String role : newUser.getRoleSet()){
			if(!existingRoles.contains(role)){
				UserRoleEntity roleEntity = new UserRoleEntity();
				roleEntity.setRole(role);
				roleEntity.setUsername(newUser.getUsername());
				roleRepository.save(roleEntity);
			}
		}
	}

	@Override
	public List<UserInfo> searchUserByName(String nameContains) {
		
		List<UserInfo> result = new LinkedList<UserInfo>();
		List<UserDetailEntity> entities = detailRepository.findByNameContainingOrderByName(nameContains);
		for(UserDetailEntity entity : entities){
			result.add(UserMapper.fromEntity(entity));
		}
		return result;
	}

	@Override
	public boolean validateUser(String user, String pass) {
		UserEntity userEntity = userRepository.findOne(user);
		boolean result = false;
		if(userEntity.getEnabled()){
			if(userEntity.getUsername().equalsIgnoreCase(user) 
					&& userEntity.getPassword().equalsIgnoreCase(pass)){
				System.out.println("====== is valid");
				result = true;
			}else{
				System.out.println("user: " + user  + ", pass: " + pass + "not match in db");
			}
		}else{
			System.out.println("====== user " + user + " is disabled");
		}
		return result;		
	}

	@Override
	public List<String> findUserRoles(String user) {
		List<String> roles = new ArrayList<String>();
		List<UserRoleEntity> entities = roleRepository.findByUsername(user);
		for(UserRoleEntity entity : entities){
			roles.add(entity.getRole());
		}
		return roles;
	}

	@Override
	public void addUserProject(String userid, Integer projectid) {
		UserProjectHistoryEntity entity = new UserProjectHistoryEntity();
		entity.setProjectId(projectid);
		entity.setUserId(userid);
		userProjectHistoryRepository.save(entity);		
	}

	@Override
	public void addUserDocument(String userid, Integer documentid) {
		UserDocumentHistoryEntity entity = new UserDocumentHistoryEntity();
		entity.setDocumentId(documentid);
		entity.setUserId(userid);
		userDocumentHistoryRepository.save(entity);
	}

	@Override
	public List<Project> getUserProject(String user) {
		
		final PageRequest pageRequest = new PageRequest(
				0, 20, Direction.DESC, "id");
//		Iterable<UserProjectHistoryEntity> entities = userProjectHistoryRepository.findAll(pageRequest);
		Iterable<UserProjectHistoryEntity> entities = userProjectHistoryRepository.findByUserIdOrderByIdDesc(user);//, pageRequest);
				
		List<Integer> projectIds = new ArrayList<Integer>();
		
		
		
		for(UserProjectHistoryEntity uphEntity : entities){
			Integer projectid = uphEntity.getProjectId();
			if(!projectIds.contains(projectid)){
				projectIds.add(uphEntity.getProjectId());	
			}
			
		}
		System.out.println("====== project ids: " + projectIds);
		List<Project> projects = new ArrayList<Project>();
		for(int i = 0; i < projectIds.size(); i++){
			ProjectEntity projectEntity = projectRepository.findOne(projectIds.get(i));
			if(projectEntity != null){
				projects.add(ProjectMapper.fromEntity(projectEntity));
			}
		}
		return projects;
	}

	@Override
	public List<Document> getUserDocument(String user) {
		final PageRequest pageRequest = new PageRequest(
				0, 20, Direction.DESC, "id");
		Iterable<UserDocumentHistoryEntity> entities = userDocumentHistoryRepository.findByUserIdOrderByIdDesc(user);//findAll(pageRequest);
		
		List<Integer> documentIds = new ArrayList<Integer>();
		for(UserDocumentHistoryEntity uchEntity : entities){
			if(!documentIds.contains(uchEntity.getDocumentId())){
				documentIds.add(uchEntity.getDocumentId());
			}
		}
		
		Map<Integer, Document> documentMap = new HashMap<Integer, Document>();
		long t1 = System.currentTimeMillis();
		Iterable<DocumentEntity> documentEntities = documentRepository.findAll(documentIds);
		long t2 = System.currentTimeMillis();
		System.out.println("=======" + (t2-t1));
		for(DocumentEntity documentEntity : documentEntities){
			documentEntity.setContent(null);
			documentMap.put(documentEntity.getId(), DocumentMapper.fromEntity(documentEntity));
		}
		List<Document> documents = new ArrayList<Document>();
		for(Integer documentId : documentIds){
			documents.add(documentMap.get(documentId));
		}
		return documents;
	}

	@Override
	public List<String> getUserSearchKeyWorld(String user) {
		List<String> keywords = new ArrayList<String>();
		List<UserSearchHistoryEntity> entities = userSearchHistoryRepository.findByUserIdOrderById(user);
		for(UserSearchHistoryEntity entity : entities){
			String keyword = entity.getKeyword();
			if(!keywords.contains(keyword)){
				keywords.add(keyword);
			}
		}		
		return keywords;
	}

	@Override
	public void addUserSearch(String userid, String keyword) {
		UserSearchHistoryEntity entity = new UserSearchHistoryEntity();
		entity.setKeyword(keyword);
		entity.setUserId(userid);
		userSearchHistoryRepository.save(entity);	
	}

	@Override
	public UserInfo getUserInfoById(String userId) {
		UserDetailEntity entity = detailRepository.findOne(userId);
		UserInfo info = UserMapper.fromEntity(entity);
		return info;
	}
}
