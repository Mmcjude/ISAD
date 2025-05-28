package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.demo.model.MyUser;
import com.example.demo.repo.IMyUserRepo;

@Service
public class MyUserDetailsManagerService implements UserDetailsManager{

	@Autowired
	IMyUserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username)
                .map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

	@Override
	public void createUser(UserDetails userDetails) {
        MyUserDetails myDetails = (MyUserDetails) userDetails;
        MyUser user = myDetails.getMyUser();
        userRepo.save(user);
	}

	@Override
	public void updateUser(UserDetails user) {
		createUser(user);

	}

	@Override
	public void deleteUser(String username) {
		MyUser user = userRepo.findByUsername(username)
		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		    userRepo.delete(user);	
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		  return userRepo.existsByUsername(username);
	}

}
