package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.requestParam.BuyItem;
import com.example.demo.model.requestParam.UserOrderRequestParam;
import com.example.demo.model.vo.OrderItem;
import com.example.demo.model.vo.Product;
import com.example.demo.model.vo.UserOrder;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.UserOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.userOrderService;

@Service
public class userOrderServiceImpl implements  userOrderService{

	@Autowired
	UserOrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	ProductRepository productRepository;

	// @Transactional ensure two table insert successfully, or rollback all data
	@Override
	@Transactional
	public Integer createOrder(Integer userId, UserOrderRequestParam userOrderRequestParam) {
		UserOrder userOrder = new UserOrder();
		userOrder.setUserId(userId);
		
		Map<Integer, Product> productMap = new HashMap<>(); 
		
		List<BuyItem> buyItemList = userOrderRequestParam.getBuyItemList();
		Integer total = buyItemList.stream().mapToInt(x -> {
				Optional<Product> productOpt = productRepository.findById(x.getProductId());
				Integer price = 0;
				
				if(productOpt.isPresent()) {
					Product product = productOpt.get();
					productMap.put(product.getProductId(), product);
					price = product.getPrice() * x.getQuantity();
				}
				
				return price;
				}).sum();
		
		userOrder.setTotalAmount(total);
		
		orderRepository.save(userOrder);
		
		List<OrderItem> orderItemList = new ArrayList<>(); 
		for(BuyItem buyItem: buyItemList) {
			OrderItem OrderItem = new OrderItem();
			Integer quantity = buyItem.getQuantity();
			Integer amount = 0;
			Product product = productMap.get(buyItem.getProductId());
			
			if(product != null) {
				amount = product.getPrice() * quantity;
			}
			
			OrderItem.setOrderId(userOrder.getOrderId());
			OrderItem.setProductId(buyItem.getProductId());
			OrderItem.setQunatity(quantity);
			OrderItem.setAmount(amount);
			
			orderItemList.add(OrderItem);
		}
		
		orderItemRepository.saveAll(orderItemList);
		
		return userOrder.getOrderId();
	}
	
}
