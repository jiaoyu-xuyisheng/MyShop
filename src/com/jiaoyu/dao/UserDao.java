package com.jiaoyu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.eclipse.jdt.internal.compiler.parser.Scanner;

import com.jiaoyu.domain.category;
import com.jiaoyu.domain.orderitem;
import com.jiaoyu.domain.orders;
import com.jiaoyu.domain.product;
import com.jiaoyu.domain.user;

import com.jiaoyu.util.DataSourceUtils;

public class UserDao {

	public int regiest(user use) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?);";
		return runner.update(sql, use.getUid(),use.getUsername(),
				use.getPassword(),use.getName(),use.getEmail(),use.getTelephone(),
				use.getBirthday(),use.getSex(),use.getState(),use.getCode());
		
	}

	public void active(String activeCode) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set state=? where code=?";
		runner.update(sql,1,activeCode);		
	}

	public boolean isExist(String username) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from user where username=?";
		Long query=(Long)runner.query(sql, new ScalarHandler(), username);
		int result=query.intValue();
		return result>0;
	}

	public user login(String username, String password) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where username=? and password=?;";
		return runner.query(sql, new BeanHandler<user>(user.class), username,password);
		
	}

	public List<product> findHotProduct() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where is_hot=? limit ?,?;";
		return runner.query(sql,new BeanListHandler<product>(product.class),1,0,9);
	}

	public List<product> findNewProduct() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product order by pdate desc limit ?,?;";
		return runner.query(sql,new BeanListHandler<product>(product.class),0,9);
	}

	public List<category> findAllCategory() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category;";
		return runner.query(sql,new BeanListHandler<category>(category.class));
	}

	public int getProductTotalCount(String cid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from product where cid=?";
		Long query=(Long)runner.query(sql, new ScalarHandler(), cid);
		return query.intValue();
	}

	public List<product> getCurrentProductList(String cid, int index, int currentCount) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where cid=? limit ?,?;";
		return runner.query(sql,new BeanListHandler<product>(product.class),cid,index,currentCount);
	}

	public product findProductInfoByPid(String pid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from  product where pid=?;";		
		return runner.query(sql, new BeanHandler<product>(product.class), pid);
	}

	public void addOrderItemToMySql(orders orders) throws SQLException {
		QueryRunner runner=new QueryRunner();
	/*	+----------+-------------+------+-----+---------+-------+
		| Field    | Type        | Null | Key | Default | Extra |
		+----------+-------------+------+-----+---------+-------+
		| itemid   | varchar(50) | NO   | PRI | NULL    |       |
		| count    | int(11)     | YES  |     | NULL    |       |
		| subtotal | double      | YES  |     | NULL    |       |
		| pid      | varchar(50) | YES  | MUL | NULL    |       |
		| oid      | varchar(50) | YES  | MUL | NULL    |       |
		+----------+-------------+------+-----+---------+-------+*/
		Connection conn = DataSourceUtils.getConnection();
		List<orderitem> orderitems = orders.getOrderitems();
		String sql="insert into orderitem values(?,?,?,?,?);";
		for (orderitem orderitem : orderitems) {
			runner.update(conn,sql,orderitem.getItemid(),orderitem.getCount(),
					orderitem.getSubtotal(),orderitem.getProduct().getPid(),orderitem.getOrders().getOid());
		}
		
		
	}

	public void addOrderToMySql(orders orders) throws SQLException {
		QueryRunner runner=new QueryRunner();
		String sql="insert into orders values(?,?,?,?,?,?,?,?);";
		Connection conn = DataSourceUtils.getConnection();
		/*+-----------+-------------+------+-----+---------+-------+
		| Field     | Type        | Null | Key | Default | Extra |
		+-----------+-------------+------+-----+---------+-------+
		| oid       | varchar(50) | NO   | PRI | NULL    |       |
		| ordertime | datetime    | YES  |     | NULL    |       |
		| total     | double      | YES  |     | NULL    |       |
		| state     | int(11)     | YES  |     | NULL    |       |
		| address   | varchar(30) | YES  |     | NULL    |       |
		| name      | varchar(20) | YES  |     | NULL    |       |
		| telephone | varchar(20) | YES  |     | NULL    |       |
		| uid       | varchar(50) | YES  |     | NULL    |       |
		+-----------+-------------+------+-----+---------+-------+*/
		runner.update(conn,sql,orders.getOid(),orders.getOrdertime(),orders.getTotal()
				,orders.getState(),orders.getAddress(),orders.getName(),orders.getTelephone(),orders.getUser().getUid());
		
	}

	public void updateOrderAdrr(orders orders) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set address=?,name=?,telephone=? where oid=?;";	
		runner.update(sql, orders.getAddress(),orders.getName(),orders.getTelephone(),orders.getOid());
		
	}

	public void updateOrderState(String r6_Order) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set state=? where oid=?;";
		runner.update(sql, 1,r6_Order);
		
	}

	public List<orders> findMyOrderByUid(String uid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders where uid=?;";
		return runner.query(sql,new BeanListHandler<orders>(orders.class),uid);
	}

	public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from orderitem i,product p where i.pid=p.pid and i.oid=?";
		return runner.query(sql,new MapListHandler(),oid);
	}

	

	



}
