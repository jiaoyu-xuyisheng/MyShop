package com.jiaoyu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;


import com.jiaoyu.domain.category;
import com.jiaoyu.domain.orders;
import com.jiaoyu.domain.product;
import com.jiaoyu.domain.user;
import com.jiaoyu.util.DataSourceUtils;

public class adminDao {

	public user login(String username, String password) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where username=? and password=?;";
		return runner.query(sql, new BeanHandler<user>(user.class), username,password);
	}

	public List<category> findAllCategory() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category";
		return runner.query(sql, new BeanListHandler<category>(category.class));
	}

	public List<product> findAllProduct() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product";
		return runner.query(sql, new BeanListHandler<product>(product.class));
	}

	public product findPoductBypid(String pid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where pid=?;";
		return runner.query(sql, new BeanHandler<product>(product.class),pid);
	}

	public void findPoductBypid(product pro) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		runner.update(sql,pro.getPname(),pro.getMarket_price(), pro.getShop_price(),
				pro.getPimage(),pro.getPdate(),pro.getIs_hot(),
				pro.getPdesc(),pro.getPflag(),pro.getCategory().getCid(),pro.getPid());
		
	}

	public void updateCategoryBycid(category category) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update category set cname=? where cid=?;";
		runner.update(sql,category.getCname(),category.getCid());
		
	}

	public void addProductByEdit(product pro) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql,pro.getPid(),pro.getPname(),pro.getMarket_price(), pro.getShop_price(),
				pro.getPimage(),pro.getPdate(),pro.getIs_hot(),
				pro.getPdesc(),pro.getPflag(),pro.getCategory().getCid());
	}

	public void delProductByPid(String pid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from product where pid=?";
		runner.update(sql,pid);

		
	}

	public void delCategoryByPid(String cid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from category where cid=?";
		runner.update(sql,cid);		
	}

	public void addCategory(category category) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into category values(?,?)";
		runner.update(sql,category.getCid(),category.getCname());
	}

	public List<orders> findAllOrder() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders;";
		return runner.query(sql, new BeanListHandler<orders>(orders.class));
	}

	public List<Map<String, Object>> showOneOrderByOid(String oid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		System.out.println(oid);
		String sql="select p.pimage,p.pname,p.shop_price,i.count,i.subtotal from orderitem i,product p where i.pid=p.pid and oid=?;";		
		return runner.query(sql, new MapListHandler(), oid);
	}

	

}
