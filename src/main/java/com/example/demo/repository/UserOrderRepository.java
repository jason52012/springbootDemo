package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.vo.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer>{

}
