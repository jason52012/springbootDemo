package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.form.OrderQueryForm;
import com.example.demo.model.requestParam.UserOrderRequestParam;
import com.example.demo.model.vo.UserOrder;
import com.example.demo.service.UserOrderService;
import com.example.demo.util.Page;

@Validated
@RestController
public class UserOrderController {

	private final static Logger log = LoggerFactory.getLogger(UserOrderController.class);
	
	@Autowired
	private UserOrderService userOrderService;
	
	
	@GetMapping("/users/{userId}/orders")
	public ResponseEntity<Page<UserOrder>> getOrders( 
	       @PathVariable Integer userId,
	       @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
	       @RequestParam(defaultValue = "0") @Min(0) Integer offset){
		
		OrderQueryForm orderQueryForm = new OrderQueryForm();
		orderQueryForm.setUserId(userId);
		orderQueryForm.setLimit(limit);
		orderQueryForm.setOffset(offset);
		
		Page<UserOrder> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		List<UserOrder> userOrderList = userOrderService.getOrders(orderQueryForm);
		Integer total = userOrderService.countOrder(orderQueryForm);
		page.setResults(userOrderList);
		page.setTotal(total);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}
	// user -> login -> payment
	@PostMapping("/users/{userId}/orders")
	public ResponseEntity<?> createOrder(@PathVariable Integer userId,
										 @RequestBody @Valid UserOrderRequestParam userOrderRequestParam){
		
		Integer orderId = userOrderService.createOrder(userId, userOrderRequestParam );
		UserOrder userOrder = userOrderService.getAllOrderItemByItemId(orderId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userOrder);
	}
	
}
