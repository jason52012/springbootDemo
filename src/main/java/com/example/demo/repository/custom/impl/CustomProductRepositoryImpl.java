package com.example.demo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.constant.ProductCategory;
import com.example.demo.model.form.ProductQueryForm;
import com.example.demo.model.vo.Product;
import com.example.demo.repository.custom.CustomProductRepository;

@Repository("CustomProductRepository")
public class CustomProductRepositoryImpl implements CustomProductRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProducts(ProductQueryForm queryForm) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT PRODUCT_ID, PRODUCT_NAME, CATEGORY, IMAGE_URL, PRICE, STOCK, DESCRIPTION, CREATE_TIME, LAST_MODIFIED_TIME ");
		
		sql = getConditionSql(sql, queryForm);
		
		// pagination
		sql.append(" LIMIT " + queryForm.getLimit() + " OFFSET " + queryForm.getOffset());
		
		Query query = entityManager.createNativeQuery(sql.toString(), Product.class);
		
		setValue(query, queryForm);
		
		return query.getResultList();
	}

	@Override
	public Integer countProducts(ProductQueryForm queryForm) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) ");
		
		sql = getConditionSql(sql, queryForm);
		
		Query query = entityManager.createNativeQuery(sql.toString());
		
		setValue(query, queryForm);
		
		int count = Integer.parseInt(query.getSingleResult().toString());
		return count;
	}
	
	private StringBuilder getConditionSql(StringBuilder sql, ProductQueryForm queryForm) {
		sql.append(" FROM PRODUCT ");
		sql.append(" WHERE 1 = 1 ");
		
		// condiiton
		if(queryForm.getCategory() != null) {
			sql.append(" AND CATEGORY = :CATEGORY ");
		}
				
		if(queryForm.getProductName() != null) {
			sql.append(" AND PRODUCT_NAME LIKE :PRODUCT_NAME ");
		}
		
		// order
		sql.append(" ORDER BY " + queryForm.getOrderBy() + " " + queryForm.getSort());
		
		return sql ;
	}
	
	private void setValue(Query query, ProductQueryForm queryForm) {
		
		if(queryForm.getCategory() != null) {
			query.setParameter("CATEGORY", queryForm.getCategory().name());
		}
		
		if(queryForm.getProductName()  != null) {
			query.setParameter("PRODUCT_NAME", "%" + queryForm.getProductName()  + "%");
		}
		
	}
	
}
