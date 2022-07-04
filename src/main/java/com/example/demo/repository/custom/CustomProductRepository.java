package com.example.demo.repository.custom;

import java.util.List;

import com.example.demo.constant.ProductCategory;
import com.example.demo.model.form.ProductQueryForm;
import com.example.demo.model.vo.Product;

public interface CustomProductRepository {

	public  List<Product> getProducts( ProductQueryForm queryForm);
	
	public  Integer countProducts( ProductQueryForm queryForm);
	
}
