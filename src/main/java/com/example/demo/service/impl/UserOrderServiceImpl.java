package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.form.OrderQueryForm;
import com.example.demo.model.grid.OrderItemGrid;
import com.example.demo.model.requestParam.BuyItem;
import com.example.demo.model.requestParam.UserOrderRequestParam;
import com.example.demo.model.vo.OrderItem;
import com.example.demo.model.vo.Product;
import com.example.demo.model.vo.User;
import com.example.demo.model.vo.UserOrder;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.UserOrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.custom.CustomOrderItemGridRepository;
import com.example.demo.repository.custom.CustomUserOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.UserOrderService;

@Service
public class UserOrderServiceImpl implements  UserOrderService{
	
	private final static Logger log = LoggerFactory.getLogger(UserOrderServiceImpl.class);

	@Autowired
	private UserOrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomOrderItemGridRepository customOrderItemGridRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomUserOrderRepository customUserOrderRepository;

	// @Transactional ensure two table insert successfully, or rollback all data
	@Override
	@Transactional
	public Integer createOrder(Integer userId, UserOrderRequestParam userOrderRequestParam) {
		User user = userRepository.getOne(userId);
		
		if(user == null) {
			log.warn("此會員ID -> {} 沒有註冊資料", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		UserOrder userOrder = new UserOrder();
		userOrder.setUserId(userId);
		
		Map<Integer, Product> productMap = new HashMap<>(); 
		
		List<BuyItem> buyItemList = userOrderRequestParam.getBuyItemList();
		Integer total = buyItemList.stream().mapToInt(x -> {
				Product product = productRepository.getOne(x.getProductId());
				Integer price = 0;
				
				if(product == null) {
					log.warn(" 商品id -> {} ，找不到", x.getProductId());
					
				} else if(product.getStock() < x.getQuantity()){
					log.warn(" 商品id -> {} , 庫存數 -> {} , 不足訂單數 -> {}", x.getProductId(), 
																		  product.getStock(), 
																		  x.getQuantity());
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				} else {
					Integer stock = product.getStock() - x.getQuantity();
					product.setStock(stock);
					product.setLastModifiedTime(new Date());
					productMap.put(product.getProductId(), product);
					price = product.getPrice() * x.getQuantity();
				}
				
				return price;
				}).sum();
		
		userOrder.setTotalAmount(total);
		
		// product stock update
		List<Product> productList = new ArrayList<>(productMap.values());
		productRepository.saveAll(productList);
		
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

	@Override
	public UserOrder findByOrderId(Integer orderId) {
		return orderRepository.getOne(orderId);
	}

	@Override
	public UserOrder getAllOrderItemByItemId(Integer orderId) {
		UserOrder  userOrder  = orderRepository.getOne(orderId);
		List<OrderItemGrid> OrderItemGridList = new ArrayList<>();
		if(userOrder != null) {
			OrderItemGridList = customOrderItemGridRepository.getAllOrderItemByItemId(orderId);
			userOrder.setOrderItemGridList(OrderItemGridList);
		}
		return userOrder;
	}

	@Override
	public List<UserOrder> getOrders(OrderQueryForm orderQueryForm) {
		return customUserOrderRepository.getOrders(orderQueryForm);
	}

	@Override
	public Integer countOrder(OrderQueryForm orderQueryForm) {
		return customUserOrderRepository.countOrder(orderQueryForm);
	}
	
}
