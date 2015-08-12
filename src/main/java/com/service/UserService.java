package com.service;

import com.model.User;

import java.util.List;

public interface UserService {
	
	public void addUser(User user);
	public void updateUser(User user);
	public User getUser(int id);
	public void deleteUser(int id);
	public List<User> getUsers();
	public List<User> getUsers(String filter);

}
