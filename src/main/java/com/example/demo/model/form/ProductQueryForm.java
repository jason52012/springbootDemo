package com.example.demo.model.form;

import com.example.demo.constant.ProductCategory;

public class ProductQueryForm {

	private String productName;
	
	private ProductCategory category;
	
	private String orderBy;
	
	private String sort;
	
	private Integer limit;
	
	private Integer offset;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "ProductQueryForm [productName=" + productName + ", category=" + category + ", orderBy=" + orderBy
				+ ", sort=" + sort + ", limit=" + limit + ", offset=" + offset + "]";
	}
	
}
