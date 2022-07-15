package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.requestParam.UserRegisterRequestParam;
import com.example.demo.model.vo.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private UserRepository userRepository;
	
	// 註冊新帳號
    @Test
    public void register_success() throws Exception {
    	UserRegisterRequestParam userRegisterRequestParam = new UserRegisterRequestParam();
    	userRegisterRequestParam.setEmail("test1@gmail.com");
    	userRegisterRequestParam.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequestParam);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.e_mail", equalTo("test1@gmail.com")))
                .andExpect(jsonPath("$.createTime", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedTime", notNullValue()));

        // 檢查資料庫中的密碼不為明碼
        User user = userRepository.findByEmail(userRegisterRequestParam.getEmail()) ;
        assertNotEquals(userRegisterRequestParam.getPassword(), user.getPassword());
    }
}
