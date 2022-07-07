package com.example.demo.model.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

	@Id
	@Column(name = "ORDER_ITEM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderItemId;
	
	@Column(name = "PRODUCT_ID", nullable=false)
	private Integer productId;
	
	@Column(name = "Order_ID", nullable=false)
	private Integer orderId;
	
	@Column(name = "QUANTITY", nullable=false)
	private Integer qunatity;
	
	@Column(name = "Amount", nullable=false)
	private Integer amount;

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

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", productId=" + productId + ", orderId=" + orderId
				+ ", qunatity=" + qunatity + ", amount=" + amount + "]";
	}

	
}
