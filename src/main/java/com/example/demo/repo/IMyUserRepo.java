package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.MyUser;

public interface IMyUserRepo extends CrudRepository<MyUser, Long>{
	public abstract boolean existsByUsername(String username);

	public abstract Optional<MyUser> findByUsername(String username);
}
