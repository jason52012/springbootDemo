package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.constant.ProductCategory;
import com.example.demo.model.form.ProductQueryForm;
import com.example.demo.model.vo.Product;
import com.example.demo.service.ProductService;
import com.example.demo.util.Page;

// ***** 開啟validator 註解
@Validated
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>>  getProducts( 
	       @RequestParam(required = false) ProductCategory productCategory,
	       @RequestParam(required = false) String productName,
	       @RequestParam(defaultValue = "CREATE_TIME") String orderBy,
	       @RequestParam(defaultValue = "DESC") String sort,
	       @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
	       @RequestParam(defaultValue = "0") @Min(0) Integer offset){
		
		ProductQueryForm queryForm = new ProductQueryForm();
		queryForm.setCategory(productCategory);
		queryForm.setProductName(productName);
		queryForm.setOrderBy(orderBy);
		queryForm.setSort(sort);
		queryForm.setLimit(limit);
		queryForm.setOffset(offset);
		
		Page<Product> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		List<Product> results = productService.getProducts(queryForm); 
		Integer total = productService.countProducts(queryForm);
		page.setResults(results);
		page.setTotal(total);
		return ResponseEntity.status(HttpStatus.OK).body(page);
	
	} 
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> findByProductId(@PathVariable Integer productId) {
		Optional<Product> product = productService.findByProductId(productId);
		
		if(product !=null) {
			return ResponseEntity.status(HttpStatus.OK).body(product.orElse(new Product()));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
			
	}
	
	@PostMapping("/products/saveProduct")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		
		
		productService.saveOrUpdate(product);
		
		Optional<Product> savedProduct = productService.findByProductId(product.getProductId());
		
		if(savedProduct.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(savedProduct.orElse(new Product()));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@DeleteMapping("/products/deleteProduct/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
			productService.deleteByProductId(productId);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
