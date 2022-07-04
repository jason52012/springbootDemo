package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.vo.Product;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	// findByXXX => select * from tableName where XXX = :XXX
	// *** 生sql 參數順位按照命名順序傳入。 用在單表
	List<Product> findByProductName(String productName); 

	
	// @Query 用於複雜表
	@Query(value = " SELECT * FROM product WHERE productId = :productId AND productName = :productName ", nativeQuery = true)
	Product test1(@Param("productId")Integer productId, @Param("productName")String productName);
	
	
	
}
