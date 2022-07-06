package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.requestParam.UserLoginRequestParam;
import com.example.demo.model.requestParam.UserRegisterRequestParam;
import com.example.demo.model.vo.User;
import com.example.demo.service.UserService;

@Validated
@Controller
public class UserController {

	@Autowired 
	private UserService userService;
	
	
	@PostMapping("/users/register")
	public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestParam userRegisterRequestParam){
		
		User newUser = userService.userRegister(userRegisterRequestParam);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
	
	@PostMapping("/users/login")
	public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequestParam userLoginRequestParam){
		User newUser = userService.userlogin(userLoginRequestParam);
		
		return ResponseEntity.status(HttpStatus.OK).body(newUser);
	}
}
