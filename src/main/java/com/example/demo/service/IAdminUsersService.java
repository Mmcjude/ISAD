package com.example.demo.service;

import java.util.List;

import com.example.demo.model.MyAuthority;
import com.example.demo.model.MyUser;

public interface IAdminUsersService {
	public abstract List<MyUser> retrieveAllUsers();
	public abstract void createUserAccount(String nickname, String password, MyAuthority authority) throws Exception;
	public abstract MyUser retrieveUserById(long id) throws Exception;
	public abstract void updateUserById(long id, String nickname, String password, MyAuthority authority) throws Exception;
	public abstract void deleteUserById(long id) throws Exception;
}
