package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import com.example.demo.constant.ProductCategory;
import com.example.demo.model.form.ProductQueryForm;
import com.example.demo.model.vo.Product;

public interface ProductService {

	public Optional<Product> findByProductId(Integer productId); 
	
	
	public Product saveOrUpdate(Product product);
	
	
	public void deleteByProductId(Integer productId);
	
	public List<Product> getProducts(ProductQueryForm queryForm);
	
	public Integer countProducts(ProductQueryForm queryForm);
}
