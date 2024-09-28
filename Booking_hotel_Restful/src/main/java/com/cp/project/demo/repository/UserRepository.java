package com.cp.project.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.project.demo.model.forUser.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	List<User> findAll();
}
