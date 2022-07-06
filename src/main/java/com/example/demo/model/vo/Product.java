package com.example.demo.model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.constant.ProductCategory;

@Entity
@Table(name = "PRODUCT")
public class Product {
	
//	@Column(name = Name, nullable=false) => nullable belong to jpa jar for db validation 
//	@NotNull belongs to JSR 303-bean validation, It has nothing to do with database constraints itself when
//	use this class need to use @Valid activate
	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	
	@Column(name = "PRODUCT_NAME", nullable=false)
	private String productName;
	
	@Column(name = "CATEGORY", nullable=false)
	@Enumerated(EnumType.STRING)
	private ProductCategory category;
	
	@Column(name = "IMAGE_URL", nullable=false)
	private String imageUrl;
	
	@Column(name = "PRICE", nullable=false)
	private Integer price;
	
	@Column(name = "STOCK", nullable=false)
	private Integer stock;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	@CreationTimestamp
	@Column(name = "CREATE_TIME", updatable= false)
	private Date createTime;
	
	@UpdateTimestamp
	@Column(name = "LAST_MODIFIED_TIME")
	private Date lastModifiedTime;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", category=" + category
				+ ", imageUrl=" + imageUrl + ", price=" + price + ", stock=" + stock + ", description=" + description
				+ ", createTime=" + createTime + ", lastModifiedTime=" + lastModifiedTime + "]";
	}
	
	
}
