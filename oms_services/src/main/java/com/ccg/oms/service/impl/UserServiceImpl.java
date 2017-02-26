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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.common.data.user.NewUser;
import com.ccg.oms.common.data.user.User;
import com.ccg.oms.common.data.user.UserInfo;
import com.ccg.oms.common.data.user.UserWithPassword;
import com.ccg.oms.dao.entiry.user.UserDetailEntity;
import com.ccg.oms.dao.entiry.user.UserEntity;
import com.ccg.oms.dao.entiry.user.UserEntity2;
import com.ccg.oms.dao.entiry.user.UserRoleEntity;
import com.ccg.oms.dao.entiry.user.UserRoleEntity2;
import com.ccg.oms.dao.repository.user.User2Repository;
import com.ccg.oms.dao.repository.user.UserDetailRepository;
import com.ccg.oms.dao.repository.user.UserRepository;
import com.ccg.oms.dao.repository.user.UserRoleRepository;
import com.ccg.oms.service.UserServices;
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
					user.getRoles().add(role.getRole());
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
			System.out.println("=================" + role);
			UserRoleEntity2 roleEntity = new UserRoleEntity2();
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
					sb.append(role.getRole()).append(", ");
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
	

}
