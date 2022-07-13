package com.example.demo.service;

import com.example.demo.model.requestParam.UserLoginRequestParam;
import com.example.demo.model.requestParam.UserRegisterRequestParam;
import com.example.demo.model.vo.User;

public interface UserService {

	public User userRegister(UserRegisterRequestParam userRegisterRequestParam);
	
	
	public User userlogin(UserLoginRequestParam userLoginRequestParam);
	
}
