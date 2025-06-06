package com.example.demo.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.MyUser;

public class MyUserDetails implements UserDetails{

    private MyUser user;

    public MyUserDetails(MyUser user) {
        this.user = user;
    }
    
    public MyUser getMyUser() {
    	return user;
	}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority().getTitle()));
    }

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		 return user.getUsername();
	}

}