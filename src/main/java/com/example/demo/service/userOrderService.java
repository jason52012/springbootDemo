package com.example.demo.service;

import com.example.demo.model.requestParam.UserOrderRequestParam;

public interface userOrderService {

	Integer createOrder(Integer userId, UserOrderRequestParam userOrderRequestParam);
}
