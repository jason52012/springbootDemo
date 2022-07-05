package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	// 查詢商品
	@Test
	public void findByProductId_success() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/{productId}", 1);
		
		mockMvc.perform(requestBuilder)
			   .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")))
			   .andExpect(jsonPath("$.category", equalTo("FOOD")))
			   .andExpect(jsonPath("$.imageUrl", notNullValue()))
			   .andExpect(jsonPath("$.price", notNullValue()))
			   .andExpect(jsonPath("$.stock", notNullValue()))
			   .andExpect(jsonPath("$.description", notNullValue()))
			   .andExpect(jsonPath("$.createTime", notNullValue()))
			   .andExpect(jsonPath("$.lastModifiedTime", notNullValue()));
	}
	
}
