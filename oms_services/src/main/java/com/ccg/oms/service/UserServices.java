package com.ccg.oms.service;

import java.util.List;

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
	
	public List<UserInfo> searchUserByName(String nameContains);
	
	public boolean validateUser(String user, String pass);
	public List<String> findUserRoles(String user);
}
