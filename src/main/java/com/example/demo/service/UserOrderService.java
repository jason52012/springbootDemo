package com.example.demo.service;

import java.util.List;

import com.example.demo.model.grid.OrderItemGrid;
import com.example.demo.model.requestParam.UserOrderRequestParam;
import com.example.demo.model.vo.UserOrder;

public interface UserOrderService {

	Integer createOrder(Integer userId, UserOrderRequestParam userOrderRequestParam);
	
	UserOrder findByOrderId(Integer orderId);
	
	UserOrder getAllOrderItemByItemId(Integer orderId);
}
