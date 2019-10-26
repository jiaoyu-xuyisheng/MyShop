package com.jiaoyu.domain;

public class orderitem {
	private String itemid;
	private int count;
	private double subtotal;
	private product product;
	private orders orders;


	public product getProduct() {
		return product;
	}

	public void setProduct(product product) {
		this.product = product;
	}

	public orders getOrders() {
		return orders;
	}

	public void setOrders(orders orders) {
		this.orders = orders;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}


}
