package com.example.demo.model.requestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserLoginRequestParam {

	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLoginRequestParam [email=" + email + ", password=" + password + "]";
	}
	
	
}
