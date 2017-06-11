package com.ccg.oms.service;

import java.util.List;

import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.user.NewUser;
import com.ccg.oms.common.data.user.User;
import com.ccg.oms.common.data.user.UserInfo;
import com.ccg.oms.common.data.user.UserWithPassword;

public interface UserServices {
	
	public User findUserById(String username);
	public List<User> getUsers();
	public void createUser(UserWithPassword user);
	public void removeUser(String username);
	public void removeFromRole(String username, String role);
	public void createUserWithRoles(User user, String password);
	public void assignToRole(String username, String role);
	public void changePassword(String username, String oldPassword, String newPassword);
	public void updateEmail(String username, String newEmail);
	
	public void createNewUser(NewUser newUser);
	public void updateUser(NewUser newUser);
	public List<UserInfo> getUserInfo();
	public UserInfo getUserInfoById(String userId);
	
	public List<UserInfo> searchUserByName(String nameContains);
	
	public boolean validateUser(String user, String pass);
	public List<String> findUserRoles(String user);
	
	public void addUserProject(String userid, Integer projectid);
	public void addUserDocument(String userid, Integer documentid);
	public void addUserSearch(String userid, String keyword);
	public List<Project> getUserProject(String user);
	public List<Document> getUserDocument(String user);
	public List<String> getUserSearchKeyWorld(String user);
}
