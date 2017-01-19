package com.ccg.oms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.common.data.user.User;
import com.ccg.oms.common.data.user.UserWithPassword;
import com.ccg.oms.dao.entiry.user.UserEntity;
import com.ccg.oms.dao.entiry.user.UserEntity2;
import com.ccg.oms.dao.entiry.user.UserRoleEntity;
import com.ccg.oms.dao.entiry.user.UserRoleEntity2;
import com.ccg.oms.dao.repository.user.User2Repository;
import com.ccg.oms.dao.repository.user.UserRepository;
import com.ccg.oms.dao.repository.user.UserRoleRepository;
import com.ccg.oms.service.UserServices;

@Service
public class UserServiceImpl implements UserServices{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	User2Repository user2Repository;	
	
	@Autowired
	UserRoleRepository roleRepository;
	
	

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


}
