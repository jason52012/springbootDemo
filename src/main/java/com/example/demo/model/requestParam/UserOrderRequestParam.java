package com.example.demo.model.requestParam;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class UserOrderRequestParam {

	@NotEmpty
	private List<BuyItem> buyItemList;

	public List<BuyItem> getBuyItemList() {
		return buyItemList;
	}

	public void setBuyItemList(List<BuyItem> buyItemList) {
		this.buyItemList = buyItemList;
	}
	
	
}
