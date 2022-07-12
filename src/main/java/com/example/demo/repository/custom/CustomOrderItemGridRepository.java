package com.example.demo.repository.custom;

import java.util.List;

import com.example.demo.model.grid.OrderItemGrid;

public interface CustomOrderItemGridRepository {

	List<OrderItemGrid> getAllOrderItemByItemId(Integer orderId);
}
