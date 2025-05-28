package com.example.demo.model;


import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "MyAuthority")
@ToString
@Entity
public class MyAuthority {
	
	@Column(name = "Aid")
	@Setter(value = AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long aid;
	
	@NotNull
	@Pattern(regexp = "[A-Z]{4,10}")
	@Column(name = "title")
	private String title;
	
	@OneToMany(mappedBy = "authority")
	@ToString.Exclude
	private Collection<MyUser> users;
	
	
	public MyAuthority(String inputTitle)
	{
		setTitle(inputTitle);
	}
		

}