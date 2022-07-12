package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.requestParam.UserOrderRequestParam;
import com.example.demo.model.vo.UserOrder;
import com.example.demo.service.UserOrderService;

@Validated
@RestController
public class UserOrderController {

	@Autowired
	private UserOrderService userOrderService;
	
	// user -> login -> payment
	@PostMapping("/users/{userId}/orders")
	public ResponseEntity<?> createOrder(@PathVariable Integer userId,
										 @RequestBody @Valid UserOrderRequestParam userOrderRequestParam){
		
		Integer orderId = userOrderService.createOrder(userId, userOrderRequestParam );
		UserOrder userOrder = userOrderService.getAllOrderItemByItemId(orderId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userOrder);
	}
	
}
