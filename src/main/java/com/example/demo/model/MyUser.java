package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Table(name = "MyUser")
@Entity
public class MyUser {

	@Column(name = "UId")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long uid;
	
	

	@NotNull
	@Pattern(regexp = "[A-Za-z. ]{2,20}", message = "Only letters are allowed. Size 2-20 ")
	@Column(name = "Username", unique = true)
	private String username;
	
	
	
	@NotNull
	//@Pattern(regexp = "[A-Za-z0-9 ,.;:]+")
	@Size(min = 5, max = 100)
	@Column(name = "Password")
	private String password;
	
	
	@ManyToOne
	@JoinColumn(name = "Aid")
	private MyAuthority authority;
	
	
	
	public MyUser(String username, String password, MyAuthority auth)
	{
		setUsername(username);
		setPassword(password);
		setAuthority(auth);
	}
	
}