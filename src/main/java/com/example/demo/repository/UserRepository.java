package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.vo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
	
}
