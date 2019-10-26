package com.jiaoyu.server;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jiaoyu.dao.UserDao;
import com.jiaoyu.domain.user;
import com.jiaoyu.util.DataSourceUtils;
import com.jiaoyu.domain.PageBean;
import com.jiaoyu.domain.category;
import com.jiaoyu.domain.orders;
import com.jiaoyu.domain.product;

public class UserService {

	public boolean regiest(user use) throws SQLException {
		UserDao dao =new UserDao();
		int isSucess=dao.regiest(use);
		return isSucess>0;
	}

	public void active(String activeCode) {
		UserDao dao =new UserDao();
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean isExist(String username) throws SQLException {
		UserDao dao =new UserDao();
		return dao.isExist(username);
	}

	public user login(String username, String password) throws SQLException {
		UserDao dao =new UserDao();
		return dao.login(username,password);
	}

	public List<product> findHotProduct() throws SQLException {
		UserDao dao =new UserDao();
		return dao.findHotProduct();
		
	}

	public List<product> findNewProduct() throws SQLException {
		UserDao dao =new UserDao();
		return dao.findNewProduct();
	}

	public List<category> findAllCategory() throws SQLException {
		UserDao dao =new UserDao();
	
		return dao.findAllCategory();
	}

	public PageBean<product> findProductListByCid(String cid,int currentPage,int currentCount) {
		UserDao dao =new UserDao();
		PageBean<product> pageBean=new PageBean<product>();
		/*	private int currentPage;
		//当前显示个数
		private int currentCount;
		//总页数
		private int totalCount;
		//总条数
		private int totalPage;
		//当前页显示的数据集合
		private List<T> list;*/
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		int totalCount=0;
		try {
			totalCount=dao.getProductTotalCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		int totalPage=(int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		int index=(currentPage-1)*currentCount;
		List<product> list=null;
		try {
			list=dao.getCurrentProductList(cid,index,currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setList(list);
		return pageBean;
	}

	public product findProductInfoByPid(String pid) throws SQLException {
		UserDao dao =new UserDao();
		return dao.findProductInfoByPid(pid);
	}

	public void addOrderToMySql(orders orders) {
		UserDao dao =new UserDao();
		try {
			DataSourceUtils.startTransaction();
			dao.addOrderToMySql(orders);
			dao.addOrderItemToMySql(orders);
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public void updateOrderAdrr(orders orders) {
		UserDao dao =new UserDao();
		try {
			dao.updateOrderAdrr(orders);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateOrderState(String r6_Order) {
		UserDao dao =new UserDao();
		try {
			dao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<orders> findMyOrderByUid(String uid) {
		UserDao dao =new UserDao();
		List<orders> myorders=null;
		try {
			myorders= dao.findMyOrderByUid(uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myorders;
	}

	public List<Map<String, Object>> findAllOrderItemByOid(String oid) {
		UserDao dao =new UserDao();
		List<Map<String, Object>> myOrderList=null;
		try {
			myOrderList= dao.findAllOrderItemByOid(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myOrderList;
		
	}





}
