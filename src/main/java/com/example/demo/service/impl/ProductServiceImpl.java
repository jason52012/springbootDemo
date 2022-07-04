package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ProductCategory;
import com.example.demo.model.form.ProductQueryForm;
import com.example.demo.model.vo.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.custom.CustomProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CustomProductRepository customProductRepository;
	
	@Override
	public Optional<Product> findByProductId(Integer productId) {
		return productRepository.findById(productId);
	}

	@Override
	public Product saveOrUpdate(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteByProductId(Integer productId) {
		 productRepository.deleteById(productId);
	}

	@Override
	public List<Product> getProducts(ProductQueryForm queryForm) {
		return customProductRepository.getProducts(queryForm);
	}

	@Override
	public Integer countProducts(ProductQueryForm queryForm) {
		return customProductRepository.countProducts(queryForm);
	}

	
	
}
