package com.example.demo.controller;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.requestParam.BuyItem;
import com.example.demo.model.requestParam.UserOrderRequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserOrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	@Transactional
	public void createOrder_success() throws Exception {
		UserOrderRequestParam userOrderRequestParam = new UserOrderRequestParam();
		List<BuyItem> buyItemList = new ArrayList<>();
		
		BuyItem buyItem1 = new BuyItem();
		buyItem1.setProductId(1);
		buyItem1.setQuantity(5);
		buyItemList.add(buyItem1);
		
		BuyItem buyItem2 = new BuyItem();
		buyItem2.setProductId(2);
		buyItem2.setQuantity(2);
		buyItemList.add(buyItem2);
		
		userOrderRequestParam.setBuyItemList(buyItemList);
		
		String json = objectMapper.writeValueAsString(userOrderRequestParam);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/{userId}/orders", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
			   .andDo(print())
			   .andExpect(status().is(201))
			   .andExpect(jsonPath("$.orderId",notNullValue()))
			   .andExpect(jsonPath("$.userId", equalTo(1)))
			   .andExpect(jsonPath("$.totalAmount", equalTo(750)))
			   .andExpect(jsonPath("$.createTime",notNullValue()))
			   .andExpect(jsonPath("$.lastModifiedTime",notNullValue()));
		
		
	}
	
	@Test
	@Transactional
	public void createOrder_emptyBuyItemList() throws Exception {
		UserOrderRequestParam userOrderRequestParam = new UserOrderRequestParam();
		List<BuyItem> buyItemList = new ArrayList<>();
		
		userOrderRequestParam.setBuyItemList(buyItemList);
		
		String json = objectMapper.writeValueAsString(userOrderRequestParam);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/{userId}/orders", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		   .andExpect(status().is(400));
	}
	
	@Test
	@Transactional
	public void createOrder_userNotExist() throws Exception {
		UserOrderRequestParam userOrderRequestParam = new UserOrderRequestParam();
		List<BuyItem> buyItemList = new ArrayList<>();
		
		BuyItem buyItem1 = new BuyItem();
		buyItem1.setProductId(1);
		buyItem1.setQuantity(5);
		buyItemList.add(buyItem1);
		
		userOrderRequestParam.setBuyItemList(buyItemList);
		
		String json = objectMapper.writeValueAsString(userOrderRequestParam);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/{userId}/orders", 200)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mockMvc.perform(requestBuilder)
		   .andExpect(status().is(400));
	}
	
	@Transactional
    @Test
    public void createOrder_productNotExist() throws Exception {
		
		UserOrderRequestParam userOrderRequestParam = new UserOrderRequestParam();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem1 = new BuyItem();
        buyItem1.setProductId(100);
        buyItem1.setQuantity(1);
        buyItemList.add(buyItem1);

        userOrderRequestParam.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(userOrderRequestParam);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
        	    .andDo(print())
                .andExpect(status().is(400));
	}
	
	@Transactional
    @Test
    public void createOrder_stockNotEnough() throws Exception {
		UserOrderRequestParam userOrderRequestParam = new UserOrderRequestParam();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem1 = new BuyItem();
        buyItem1.setProductId(1);
        buyItem1.setQuantity(1000);
        buyItemList.add(buyItem1);

        userOrderRequestParam.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(userOrderRequestParam);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
	}
	
	// 查詢訂單列表
    @Test
    public void getOrders() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 1);
    	
    	mockMvc.perform(requestBuilder)
    		   .andDo(print())
    		   .andExpect(status().isOk())
    		   .andExpect(jsonPath("$.limit", notNullValue()))
               .andExpect(jsonPath("$.offset", notNullValue()))
               .andExpect(jsonPath("$.total", notNullValue()))
               .andExpect(jsonPath("$.results", hasSize(2)))
               .andExpect(jsonPath("$.results[0].orderId", notNullValue()))
               .andExpect(jsonPath("$.results[0].userId", equalTo(1)))
               .andExpect(jsonPath("$.results[0].totalAmount", equalTo(100000)))
               .andExpect(jsonPath("$.results[0].orderItemGridList", hasSize(1)))
               .andExpect(jsonPath("$.results[0].createTime", notNullValue()))
               .andExpect(jsonPath("$.results[0].lastModifiedTime", notNullValue()))
               .andExpect(jsonPath("$.results[1].orderId", notNullValue()))
               .andExpect(jsonPath("$.results[1].userId", equalTo(1)))
               .andExpect(jsonPath("$.results[1].totalAmount", equalTo(500690)))
               .andExpect(jsonPath("$.results[1].orderItemGridList", hasSize(3)))
               .andExpect(jsonPath("$.results[1].createTime", notNullValue()))
               .andExpect(jsonPath("$.results[1].lastModifiedTime", notNullValue()));
    	
    }
    @Test
    public void getOrders_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 1)
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }
    
    @Test
    public void getOrders_userHasNoOrder() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 2);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }
    
    @Test
    public void getOrders_userNotExist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 100);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }
}
