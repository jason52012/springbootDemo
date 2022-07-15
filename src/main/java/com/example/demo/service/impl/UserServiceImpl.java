package com.example.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.requestParam.UserLoginRequestParam;
import com.example.demo.model.requestParam.UserRegisterRequestParam;
import com.example.demo.model.vo.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public User userRegister(UserRegisterRequestParam userRegisterRequestParam) {
		User oldUser = userRepository.findByEmail(userRegisterRequestParam.getEmail());
		User newUser = new User();
		
		if(oldUser != null) {
			log.warn("該 email {} 已被註冊 -> ",  oldUser.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		// 密碼新增前，先用MD5加密取得雜奏值，雖說有碰撞機率(此忽略不計)
		String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequestParam.getPassword().getBytes());
		newUser.setEmail(userRegisterRequestParam.getEmail());
		newUser.setPassword(hashedPassword);
		
		
		
		return userRepository.save(newUser);
	}

	@Override
	public User userlogin(UserLoginRequestParam userLoginRequestParam) {
		User oldUser = userRepository.findByEmail(userLoginRequestParam.getEmail());
		
		if(userLoginRequestParam == null) {
			log.warn("該 email -> {} 尚未被會員註冊過 ",  userLoginRequestParam.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequestParam.getPassword().getBytes());
		if(oldUser!= null && oldUser.getPassword().equals(hashedPassword)) {
			return oldUser;
		}else {
			log.warn("該 email -> {}  未被註冊會員 ",  userLoginRequestParam.getEmail(), userLoginRequestParam.getPassword());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}	
			
	}

}
