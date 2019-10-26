package com.jiaoyu.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	//n个购物项
	private Map<String,cartItem> cartItems=new HashMap<String,cartItem>();
	//总计
	private double total;
	
	public Map<String, cartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, cartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

}
