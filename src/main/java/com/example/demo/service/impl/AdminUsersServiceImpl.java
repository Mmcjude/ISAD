package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.MyAuthority;
import com.example.demo.model.MyUser;
import com.example.demo.repo.IMyAuthorityRepo;
import com.example.demo.repo.IMyUserRepo;
import com.example.demo.service.IAdminUsersService;

@Service
public class AdminUsersServiceImpl implements IAdminUsersService{
	
	@Autowired
	private IMyUserRepo myUserRepo;
	
	@Autowired
	private IMyAuthorityRepo authRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;



	@Override
	public List<MyUser> retrieveAllUsers() {
		ArrayList<MyUser> users = myUserRepo.findAllByOrderByUidAsc();
		return users;
	}

	@Override
	public void createUserAccount(String nickname, String password, MyAuthority authority)
			throws Exception {
		if(nickname == null || nickname.trim().isEmpty() ||
				password == null || password.trim().isEmpty() ||
				authority == null) {
			throw new Exception("Input parameter(s) can not be null or empty.");
		}
		
		if(!nickname.trim().matches("[A-Za-z. ]{2,20}")) {
			throw new Exception("Input nickname does not match the allowed format.");
		}
		
		if(password.trim().length() < 5 || password.trim().length() > 100) {
			throw new Exception("Input password does not match the allowed format.");
		}
		
		if (myUserRepo.existsByUsername(nickname.trim())) {
		    throw new Exception("User with the same username already exists.");
		}
		
		MyUser user = new MyUser(nickname, passwordEncoder.encode(password), authority);
        
        myUserRepo.save(user); 	
	}

	@Override
	public MyUser retrieveUserById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Input '" + id + "' id is negative or equal to 0.");
		}
		
		if(!myUserRepo.existsById(id)) {
			throw new Exception("User with id " + id + " does not exist.");
		}
		
		return myUserRepo.findById(id).get();
	}

	@Override
	public void updateUserById(long id, String nickname, String password, MyAuthority authority)
			throws Exception {
		if(nickname == null || nickname.trim().isEmpty() ||
				authority == null) {
			throw new Exception("Input parameter(s) can not be null or empty.");
		}
		
		if(!nickname.trim().matches("[A-Za-z. ]{2,20}")) {
			throw new Exception("Input nickname does not match the allowed format.");
		}
		
		MyUser foundUser = retrieveUserById(id);

		    Optional<MyUser> duplicateByUsername = myUserRepo.findByUsername(nickname);
		    if (duplicateByUsername.isPresent() && duplicateByUsername.get().getUid() != id) {
		        throw new Exception("Another user with the same username exists.");
		    }

		    foundUser.setUsername(nickname.trim());;
		    if (password != null && !password.trim().isEmpty()) {
		        if (password.trim().length() < 5 || password.trim().length() > 100) {
		            throw new Exception("Input password does not match the allowed format.");
		        }
		        foundUser.setPassword(passwordEncoder.encode(password.trim()));
		    }
		    foundUser.setAuthority(authority);

		    myUserRepo.save(foundUser);
		
	}

	@Override
	public void deleteUserById(long id) throws Exception {
		if(retrieveUserById(id).getAuthority().getTitle().equals("ADMIN")) {
			throw new Exception("You cant delete the user with authority ADMIN.");
		}
		myUserRepo.delete(retrieveUserById(id));
		
	}
}
