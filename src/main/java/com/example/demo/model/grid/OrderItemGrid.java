package com.example.demo.model.grid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderItemGrid {
	@Id
	@Column(name = "ORDER_ITEM_ID")
	private Integer orderItemId;
	
	@Column(name = "PRODUCT_ID")
	private Integer productId;
	
	@Column(name = "Order_ID")
	private Integer orderId;
	
	@Column(name = "QUANTITY")
	private Integer qunatity;
	
	@Column(name = "Amount")
	private Integer amount;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "IMAGE_URL")
	private String imageUrl;

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQunatity() {
		return qunatity;
	}

	public void setQunatity(Integer qunatity) {
		this.qunatity = qunatity;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "OrderItemGrid [orderItemId=" + orderItemId + ", productId=" + productId + ", orderId=" + orderId
				+ ", qunatity=" + qunatity + ", amount=" + amount + ", productName=" + productName + ", imageUrl="
				+ imageUrl + "]";
	}
	
}
