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

import com.example.demo.model.requestParam.UserLoginRequestParam;
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
    
    @Test
    public void register_invalidEmailFormat() throws Exception {
    	UserRegisterRequestParam userRegisterRequestParam = new UserRegisterRequestParam();
    	userRegisterRequestParam.setEmail("QQ1234");
    	userRegisterRequestParam.setPassword("123");
    	
    	String json = objectMapper.writeValueAsString(userRegisterRequestParam);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        
        mockMvc.perform(requestBuilder)
        	   .andExpect(status().is(400));
    }
    
    @Test
    public void register_emailAlreadyExist() throws Exception {
    	UserRegisterRequestParam userRegisterRequestParam = new UserRegisterRequestParam();
    	userRegisterRequestParam.setEmail("test1@gmail.com");
    	userRegisterRequestParam.setPassword("123");
    	
    	String json = objectMapper.writeValueAsString(userRegisterRequestParam);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    	
    	mockMvc.perform(requestBuilder)
               .andExpect(status().is(201));

    	// 再次使用同個 email 註冊
    	mockMvc.perform(requestBuilder)
               .andExpect(status().is(400));
    }
    
    // 登入
    @Test
    public void login_success() throws Exception {
    	UserRegisterRequestParam userRegisterRequestParam = new UserRegisterRequestParam();
    	userRegisterRequestParam.setEmail("test3@gmail.com");
    	userRegisterRequestParam.setPassword("123");
    	
    	register(userRegisterRequestParam);
    	
    	UserLoginRequestParam userLoginRequestParam = new UserLoginRequestParam();
    	userLoginRequestParam.setEmail(userRegisterRequestParam.getEmail());
    	userLoginRequestParam.setPassword(userRegisterRequestParam.getPassword());
    	
    	String json = objectMapper.writeValueAsString(userLoginRequestParam);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
    			.post("/users/login")
    			.contentType(MediaType.APPLICATION_JSON)
                .content(json);
    	
    	mockMvc.perform(requestBuilder)
               .andExpect(status().is(200))
               .andExpect(jsonPath("$.userId", notNullValue()))
               .andExpect(jsonPath("$.e_mail", equalTo(userRegisterRequestParam.getEmail())))
               .andExpect(jsonPath("$.createTime", notNullValue()))
               .andExpect(jsonPath("$.lastModifiedTime", notNullValue()));
    }
    
    @Test
    public void login_wrongPassword() throws Exception {
    	UserRegisterRequestParam userRegisterRequestParam = new UserRegisterRequestParam();
    	userRegisterRequestParam.setEmail("test3@gmail.com");
    	userRegisterRequestParam.setPassword("123");
    	
    	register(userRegisterRequestParam);
    	
    	UserLoginRequestParam userLoginRequestParam = new UserLoginRequestParam();
    	userLoginRequestParam.setEmail(userRegisterRequestParam.getEmail());
    	userLoginRequestParam.setPassword("unknownQQQ");
    	
    	String json = objectMapper.writeValueAsString(userLoginRequestParam);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
    			.post("/users/login")
    			.contentType(MediaType.APPLICATION_JSON)
                .content(json);
    	
    	mockMvc.perform(requestBuilder)
        .andExpect(status().is(400));
    }
    
    @Test
    public void login_invalidEmailFormat() throws Exception {
    	UserRegisterRequestParam userRegisterRequestParam = new UserRegisterRequestParam();
    	userRegisterRequestParam.setEmail("XFRHFHIHFI");
    	userRegisterRequestParam.setPassword("123");
        
    	String json = objectMapper.writeValueAsString(userRegisterRequestParam);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
    			.post("/users/login")
    			.contentType(MediaType.APPLICATION_JSON)
                .content(json);
    	
    	mockMvc.perform(requestBuilder)
        .andExpect(status().is(400));
    }
    
    @Test
    public void login_emailNotExist() throws Exception {
    	UserLoginRequestParam userLoginRequestParam = new UserLoginRequestParam();
    	userLoginRequestParam.setEmail("unknown@gmail.com");
    	userLoginRequestParam.setPassword("unknownQQQ");
    	
    	String json = objectMapper.writeValueAsString(userLoginRequestParam);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
    			.post("/users/login")
    			.contentType(MediaType.APPLICATION_JSON)
                .content(json);
    	
    	mockMvc.perform(requestBuilder)
               .andExpect(status().is(400));
    }
    
    private void register(UserRegisterRequestParam userRegisterRequestParam) throws Exception {
        String json = objectMapper.writeValueAsString(userRegisterRequestParam);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }
}