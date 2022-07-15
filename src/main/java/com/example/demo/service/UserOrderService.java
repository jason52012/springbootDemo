package com.example.demo.service;


import java.util.List;

import com.example.demo.model.form.OrderQueryForm;
import com.example.demo.model.requestParam.UserOrderRequestParam;
import com.example.demo.model.vo.UserOrder;

public interface UserOrderService {

	Integer createOrder(Integer userId, UserOrderRequestParam userOrderRequestParam);
	
	UserOrder findByOrderId(Integer orderId);
	
	UserOrder getAllOrderItemByOrderId(Integer OrderId);
	
	List<UserOrder> getOrders(OrderQueryForm orderQueryForm);
	
	Integer countOrder(OrderQueryForm orderQueryForm);
}
