package com.example.demo.model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userid;
	
	@JsonProperty("e_mail")
	@Column(name = "EMAIL", nullable=false)
	private String email;
	
	@JsonIgnore
	@Column(name = "PASSWORD", nullable=false)
	private String password;
	
	@CreationTimestamp
	@Column(name = "CREATE_TIME", updatable= false, nullable=false)
	private Date createTime;
	
	@UpdateTimestamp
	@Column(name = "LAST_MODIFIED_TIME", nullable=false)
	private Date lastModifiedTime;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", email=" + email + ", password=" + password + ", createTime=" + createTime
				+ ", lastModifiedTime=" + lastModifiedTime + "]";
	}

	
	
}
