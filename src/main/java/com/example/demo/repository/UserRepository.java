package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.vo.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
	
}
