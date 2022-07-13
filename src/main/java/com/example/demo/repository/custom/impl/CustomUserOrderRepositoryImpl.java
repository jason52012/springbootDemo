package com.example.demo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.model.form.OrderQueryForm;
import com.example.demo.model.vo.UserOrder;
import com.example.demo.repository.custom.CustomUserOrderRepository;

@Repository("CustomUserOrderRepository")
public class CustomUserOrderRepositoryImpl implements CustomUserOrderRepository{

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserOrder> getOrders(OrderQueryForm orderQueryForm) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ORDER_ID, USER_ID, TOTAL_AMOUNT, CREATE_TIME, LAST_MODIFIED_TIME ");
		
		sql = getConditionSql(sql, orderQueryForm);
		
		Query query = entityManager.createNativeQuery(sql.toString(), UserOrder.class);
		
		setValue(query, orderQueryForm);
		
		return query.getResultList();
	}

	@Override
	public Integer countOrder(OrderQueryForm orderQueryForm) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) ");
		
		sql = getConditionSql(sql, orderQueryForm);
		
		Query query = entityManager.createNativeQuery(sql.toString());
		
		setValue(query, orderQueryForm);
		
		int count = Integer.parseInt(query.getSingleResult().toString());
		return count;
	}
	
	private StringBuilder getConditionSql(StringBuilder sql, OrderQueryForm orderQueryForm){
		sql.append(" FROM USER_ORDER ");
		sql.append(" WHERE 1 = 1 ");
		
		// condiiton
		if(orderQueryForm.getUserId() != null) {
			sql.append(" AND USER_ID = :USER_ID ");
		}
		
		// order
		sql.append(" ORDER BY CREATE_TIME DESC ");
				
		// pagination
		sql.append(" LIMIT " + orderQueryForm.getLimit() + " OFFSET " + orderQueryForm.getOffset());
				
		return sql;
	}
	
	private void setValue(Query query, OrderQueryForm orderQueryForm) {
		if(orderQueryForm.getUserId() != null) {
			query.setParameter("USER_ID", orderQueryForm.getUserId());
		}
	}
}
