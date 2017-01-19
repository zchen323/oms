package com.ccg.oms.service;

import java.util.List;

import com.ccg.oms.common.data.user.User;
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
}
