package com.example.demo.model.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.model.grid.OrderItemGrid;

@Entity
@Table(name = "USER_ORDER")
public class UserOrder {

	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	@Column(name = "USER_ID", nullable=false)
	private Integer userId;
	
	@Column(name = "TOTAL_AMOUNT", nullable=false)
	private Integer totalAmount;
	
	@CreationTimestamp
	@Column(name = "CREATE_TIME", updatable= false, nullable=false)
	private Date createTime;
	
	@UpdateTimestamp
	@Column(name = "LAST_MODIFIED_TIME", nullable=false)
	private Date lastModifiedTime;
	
	@Transient
	List<OrderItemGrid> OrderItemGridList ;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
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

	public List<OrderItemGrid> getOrderItemGridList() {
		return OrderItemGridList;
	}

	public void setOrderItemGridList(List<OrderItemGrid> orderItemGridList) {
		OrderItemGridList = orderItemGridList;
	}

	

}
