package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.constant.ProductCategory;
import com.example.demo.model.vo.Product;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductService productService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
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
	
	@Test
    public void getProducts_notFound() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 20000);

        mockMvc.perform(requestBuilder)
               .andExpect(status().is(404));
	}
	
	// 創建商品
    @Transactional
    @Test
    public void saveProduct_success() throws Exception {
    	Product product = new Product();
        product.setProductName("test food product");
        product.setCategory(ProductCategory.FOOD);
        product.setImageUrl("http://test.com");
        product.setPrice(100);
        product.setStock(2);
        
        String json = objectMapper.writeValueAsString(product);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products/saveProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        
        mockMvc.perform(requestBuilder)
        	   .andExpect(status().is(201))
        	   .andExpect(jsonPath("$.productName", equalTo("test food product")))
        	   .andExpect(jsonPath("$.category", equalTo("FOOD")))
        	   .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
        	   .andExpect(jsonPath("$.price", equalTo(100)))
        	   .andExpect(jsonPath("$.stock", equalTo(2)))
        	   .andExpect(jsonPath("$.description", nullValue()))
        	   .andExpect(jsonPath("$.createTime", notNullValue()))
        	   .andExpect(jsonPath("$.lastModifiedTime", notNullValue()));
    }
    
    @Transactional
    @Test
    public void saveProduct_illegalArgument() throws Exception {
    	Product product = new Product();
        product.setProductName("test food product");
        
        String json = objectMapper.writeValueAsString(product);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products/saveProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        
        mockMvc.perform(requestBuilder)
        	   .andExpect(status().is(400));
    }
	
    // 更新商品
    @Transactional
    @Test
    public void updateProduct_success() throws Exception {
    	
    	Optional<Product> productOpt = productService.findByProductId(3);
    	if(productOpt.isPresent()) {
    		Product product = productOpt.get();
    		product.setProductName("test food product");
            product.setCategory(ProductCategory.FOOD);
            product.setImageUrl("http://test.com");
            product.setPrice(100);
            product.setStock(2);
            
            String json = objectMapper.writeValueAsString(product);
            
            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post("/products/saveProduct")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json);
            
            mockMvc.perform(requestBuilder)
            	   .andExpect(status().is(200))
            	   .andExpect(jsonPath("$.productName", equalTo("test food product")))
            	   .andExpect(jsonPath("$.category", equalTo("FOOD")))
            	   .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
            	   .andExpect(jsonPath("$.price", equalTo(100)))
            	   .andExpect(jsonPath("$.stock", equalTo(2)))
            	   .andExpect(jsonPath("$.description", nullValue()))
            	   .andExpect(jsonPath("$.createTime", notNullValue()))
            	   .andExpect(jsonPath("$.lastModifiedTime", notNullValue()));
    	}
        
    }
    
    @Transactional
    @Test
    public void updateProduct_illegalArgument() throws Exception {
    	Optional<Product> productOpt = productService.findByProductId(3);
    	if(productOpt.isPresent()) {
    		Product product = productOpt.get();
    		product.setProductName("test food product");
            product.setCategory(null);
            product.setImageUrl("http://test.com");
            product.setPrice(100);
            product.setStock(2);
            
            String json = objectMapper.writeValueAsString(product);
            
            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post("/products/saveProduct")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json);
            
            mockMvc.perform(requestBuilder)
            	   .andExpect(status().is(400));
    	}
    }
    
    // 刪除商品
    @Transactional
    @Test
    public void deleteProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/deleteProduct/{productId}", 5);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }
    
    @Transactional
    @Test
    public void deleteProduct_deleteNonExistingProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/deleteProduct/{productId}", 20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }
    
    // 查詢商品列表
    @Test
    public void getProducts() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");
    	
    	mockMvc.perform(requestBuilder)
        	   .andDo(print())
        	   .andExpect(status().isOk())
        	   .andExpect(jsonPath("$.limit", notNullValue()))
        	   .andExpect(jsonPath("$.offset", notNullValue()))
        	   .andExpect(jsonPath("$.total", notNullValue()))
        	   .andExpect(jsonPath("$.results", hasSize(5)));
    }
    
    @Test
    public void getProducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("productName", "B")
                .param("category", "CAR");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)));
    }
    
    @Test
    public void getProducts_sorting() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[2].productId", equalTo(7)))
                .andExpect(jsonPath("$.results[3].productId", equalTo(4)))
                .andExpect(jsonPath("$.results[4].productId", equalTo(2)));
    }
    
    @Test
    public void getProducts_pagination() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(4)));
    }
}
