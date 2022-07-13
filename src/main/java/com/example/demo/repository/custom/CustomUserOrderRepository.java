package com.example.demo.repository.custom;

import java.util.List;

import com.example.demo.model.form.OrderQueryForm;
import com.example.demo.model.vo.UserOrder;

public interface CustomUserOrderRepository {

	List<UserOrder> getOrders(OrderQueryForm orderQueryForm);
	
	Integer countOrder(OrderQueryForm orderQueryForm);
}
