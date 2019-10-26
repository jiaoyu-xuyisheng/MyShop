package com.jiaoyu.server;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import com.jiaoyu.dao.adminDao;
import com.jiaoyu.domain.category;
import com.jiaoyu.domain.orders;
import com.jiaoyu.domain.product;
import com.jiaoyu.domain.user;

public class adminService {

	public user login(String username, String password) {
		adminDao dao=new adminDao();
		user use=null;
		try {
			 use= dao.login(username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return use;
	}

	public List<category> findAllCategory() {
		adminDao dao=new adminDao();
		List<category> mycategoryList=null;
		try {
			mycategoryList = dao.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mycategoryList;
	}

	public List<product> findAllProduct() {
		adminDao dao=new adminDao();
		List<product> myproductList=null;
		try {
			myproductList = dao.findAllProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myproductList;
	}

	public product findPoductBypid(String pid) {
		adminDao dao=new adminDao();
		product pro=null;
		try {
			pro= dao.findPoductBypid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pro;
	}

	public void findPoductBypid(product product) {
		adminDao dao=new adminDao();
		try {
			dao.findPoductBypid(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateCategoryBycid(category category) throws SQLException {
		adminDao dao=new adminDao();
		dao.updateCategoryBycid(category);
		
	}

	public void addProductByEdit(product product) {
		adminDao dao=new adminDao();
		try {
			dao.addProductByEdit(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void delProductByPid(String pid) {
		adminDao dao=new adminDao();
		try {
			dao.delProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void delCategoryByPid(String cid) {
		adminDao dao=new adminDao();
		try {
			dao.delCategoryByPid(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void addCategory(category category) {
		adminDao dao=new adminDao();
		try {
			dao.addCategory(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public List<orders> findAllOrder() {
		adminDao dao=new adminDao();
		List<orders> ordersList=null;
		try {
			ordersList = dao.findAllOrder();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ordersList;
	}

	public List<Map<String, Object>> showOneOrderByOid(String oid) {
		adminDao dao=new adminDao();
		List<Map<String, Object>> mapList=null;
		try {
			mapList = dao.showOneOrderByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	
	}

	


}
