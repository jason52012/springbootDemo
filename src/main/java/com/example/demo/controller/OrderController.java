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
import com.example.demo.service.userOrderService;

@Validated
@RestController
public class OrderController {

	@Autowired
	private userOrderService orderService;
	
	// user -> login -> payment
	@PostMapping("/users/{userId}/orders")
	public ResponseEntity<?> createOrder(@PathVariable Integer userId,
										 @RequestBody @Valid UserOrderRequestParam userOrderRequestParam){
		
		Integer orderId = orderService.createOrder(userId, userOrderRequestParam );
		return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
	}
	
}
