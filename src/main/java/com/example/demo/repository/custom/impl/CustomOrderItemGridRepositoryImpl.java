package com.example.demo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.model.grid.OrderItemGrid;
import com.example.demo.repository.custom.CustomOrderItemGridRepository;

@Repository("CustomOrderItemGridRepository")
public class CustomOrderItemGridRepositoryImpl implements CustomOrderItemGridRepository{

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemGrid> getAllOrderItemByItemId(Integer orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT oi.ORDER_ITEM_ID, oi.ORDER_ID, oi.ORDER_ID, oi.PRODUCT_ID, oi.QUANTITY, oi.AMOUNT, p.PRODUCT_NAME, p.IMAGE_URL ");
		sql.append(" FROM ORDER_ITEM oi ");
		sql.append(" LEFT JOIN PRODUCT p ON p.PRODUCT_ID = oi.PRODUCT_ID ");
		sql.append(" WHERE oi.ORDER_ID = :ORDER_ID ");
		
		Query query = entityManager.createNativeQuery(sql.toString(), OrderItemGrid.class);
		query.setParameter("ORDER_ID", orderId);
		
		return query.getResultList();
	}

}
