package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.MyAuthority;

public interface IMyAuthorityRepo extends CrudRepository <MyAuthority, Long>{

}
