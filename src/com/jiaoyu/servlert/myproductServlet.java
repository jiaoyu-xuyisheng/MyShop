package com.jiaoyu.servlert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.jiaoyu.domain.Cart;
import com.jiaoyu.domain.PageBean;
import com.jiaoyu.domain.cartItem;
import com.jiaoyu.domain.category;
import com.jiaoyu.domain.orderitem;
import com.jiaoyu.domain.orders;
import com.jiaoyu.domain.product;
import com.jiaoyu.domain.user;
import com.jiaoyu.server.UserService;
import com.jiaoyu.util.PaymentUtil;
import com.jiaoyu.util.jedisUtil;

import redis.clients.jedis.Jedis;

public class myproductServlet extends BaseServlet {	
	
	//我的订单
	public void myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		user use = (user)session.getAttribute("use");
		UserService service=new UserService();
		if(use==null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		List<orders> myorders=service.findMyOrderByUid(use.getUid());
		if(myorders!=null) {
			for (orders orders : myorders) {
				String oid =orders.getOid();
				List<Map<String,Object>> myorderitems=service.findAllOrderItemByOid(oid);
				for (Map<String, Object> map : myorderitems) {
					
					try {
						//创建一个orderitem实体用来封装数据；
						orderitem orderitem=new orderitem();
						BeanUtils.populate(orderitem, map);
						//创建一个product实体用封装数据;
						product pro=new product();
						BeanUtils.populate(pro, map);
						//装product数据封装到orderitem里；完成orderitem的封装;
						orderitem.setProduct(pro);
						//将orderitems数据封到List<orderitems>里;s
						orders.getOrderitems().add(orderitem);				
					} catch (IllegalAccessException |InvocationTargetException e) {
						
						e.printStackTrace();
					} 
				}
			}
		}
		session.setAttribute("myorders", myorders);
		response.sendRedirect(request.getContextPath()+"/order_list.jsp");
		
	}
	
	//付钱！！！
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				Map<String, String[]> parameterMap = request.getParameterMap();
				orders orders=new orders();		
				try {
					BeanUtils.populate(orders, parameterMap);
				} catch (IllegalAccessException |InvocationTargetException e) {					
					e.printStackTrace();
				}
				UserService service=new UserService();
				service.updateOrderAdrr(orders);
				String orderid = request.getParameter("oid");
				/*String money=orders.getTotal()+"";*/
				String money="0.01";//支付金额
				String pd_FrpId=request.getParameter("pd_FrpId");
				
				//需要发送给支付公司的数据！！
				String p0_Cmd="Buy";//购买！！
				//密钥
				String p1_MerId=ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
				String p2_Order=orderid;
				String p3_Amt=money;
				String p4_Cur="CNY";
				String p5_Pid = "";
				String p6_Pcat = "";
				String p7_Pdesc = "";
				// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
				// 第三方支付可以访问网址
				String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
				String p9_SAF = "";
				String pa_MP = "";
				String pr_NeedResponse = "1";
				// 加密hmac 需要密钥
				String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
						"keyValue");
				String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
						p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
						pd_FrpId, pr_NeedResponse, keyValue);
				String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
						"&p0_Cmd="+p0_Cmd+
						"&p1_MerId="+p1_MerId+
						"&p2_Order="+p2_Order+
						"&p3_Amt="+p3_Amt+
						"&p4_Cur="+p4_Cur+
						"&p5_Pid="+p5_Pid+
						"&p6_Pcat="+p6_Pcat+
						"&p7_Pdesc="+p7_Pdesc+
						"&p8_Url="+p8_Url+
						"&p9_SAF="+p9_SAF+
						"&pa_MP="+pa_MP+
						"&pr_NeedResponse="+pr_NeedResponse+
						"&hmac="+hmac;
				//重定向到第三方支付平台
				response.sendRedirect(url);

	}
	//确认提单！！
	public void submitOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		user use=(user) session.getAttribute("use");
		if(use==null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		UserService service=new UserService();
		orders orders=new orders();
		Cart cart=(Cart) session.getAttribute("cart");
		/*private String oid;
		private Date ordertime;
		private double total;
		private int state;
		private String address;
		private String name;
		private String telephone;
		private user user;
		private List<orderitem> orderitems=new ArrayList<orderitem>();*/
		orders.setOid(UUID.randomUUID().toString());
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = simpleDate.format(new Date());
		orders.setOrdertime(Timestamp.valueOf(format));
		orders.setTotal(cart.getTotal());
		orders.setState(0);//0表示没有下单，1表示己下单。
		orders.setAddress(null);
		orders.setName(null);
		orders.setTelephone(null);
		orders.setUser(use);
		//得到订单项！！！并将订单项封装到orderitem里;
		Map<String, cartItem> cartItems = cart.getCartItems();
		for (Map.Entry<String, cartItem>  entry : cartItems.entrySet()) {
			orderitem items=new orderitem();
			items.setItemid(UUID.randomUUID().toString());
			cartItem cartitem = entry.getValue();
			items.setCount(cartitem.getBuyNum());
			items.setProduct(cartitem.getProduct());
			items.setSubtotal(cartitem.getSubtotal());
			items.setOrders(orders);
			orders.getOrderitems().add(items);
		}
		service.addOrderToMySql(orders);
		session.setAttribute("orders", orders);
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");			
		
	}
	
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
			session.removeAttribute("cart");
			response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid=request.getParameter("pid");
		HttpSession session = request.getSession();
		Cart cart=(Cart) session.getAttribute("cart");
		if(cart!=null) {
			Map<String, cartItem> cartItems = cart.getCartItems();
			cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());
			cartItems.remove(pid);
			cart.setCartItems(cartItems);
		}
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");				
	}
	
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pid=request.getParameter("pid");
		int buyNum=Integer.parseInt(request.getParameter("buyNum"));
		UserService service=new UserService();
		//通过pid得到product对象；
		product	product=null;
		try {
		product=service.findProductInfoByPid(pid);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double subtotal=buyNum*product.getShop_price();
		//封装carItem对象;
		cartItem cartitem=new cartItem();	
		cartitem.setBuyNum(buyNum);
		cartitem.setProduct(product);
		cartitem.setSubtotal(subtotal);
		//先判断session中是否存在cart对象;
		Cart cart=(Cart) session.getAttribute("cart");
		if(cart==null) {
			cart=new Cart();
		}
		Map<String,cartItem> cartItems=cart.getCartItems();
		double newSubTotal =0.0;
		if(cartItems.containsKey(pid)) {
			cartItem item = cartItems.get(pid);
			int buyNum2 =item.getBuyNum();
			int buyNum3=buyNum+buyNum2;
			item.setBuyNum(buyNum3);
			cart.setCartItems(cartItems);		
			double oldsubtotal = item.getSubtotal();
			 newSubTotal=buyNum*product.getShop_price();
			item.setSubtotal(newSubTotal+oldsubtotal);
			
		}else {
			
			//将购物项放到购物车中
			 cart.getCartItems().put(product.getPid(), cartitem);
			 newSubTotal=buyNum*product.getShop_price();
		}
		//计算总计，也就是每个cartitem里的subtotal的和;
		double total=cart.getTotal()+newSubTotal;		
		 cart.setTotal(total);
		 //将cart放到session中
		 session.setAttribute("cart", cart);
		 response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}
	
	//激活帐号
	public void active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得激活码
		String activeCode = request.getParameter("activeCode");
		
		UserService service = new UserService();
		service.active(activeCode);
		response.sendRedirect(request.getContextPath()+"/login.jsp");

	}
	
	//ajax得到导航列表！！！
	public void categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserService service=new UserService();
		//1.获得缓存中查询categoryList 如果有直接使用 没有在从数据库中查询 存到缓存中;
		Jedis jedis=jedisUtil.getPool();
		String AllCategoryJson = jedis.get("AllCategoryJson");
		//2.判断AllCategoryJson是否为空
		if(AllCategoryJson==null) {
			System.out.println("缓存没有数据 查询数据库");
			List<category> AllCategory=null;
			try {
				AllCategory=service.findAllCategory();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Gson gson=new Gson();
			AllCategoryJson=gson.toJson(AllCategory);
			jedis.set("AllCategoryJson", AllCategoryJson);
			
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(AllCategoryJson);		
	}
	
	//得到首页信息
	public void index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService service=new UserService();
		//获取最热商品信息;
		List<product> HotProductList=null;
		try {
			HotProductList=service.findHotProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获取最新产品信息
		List<product> newProductList=null;
		try {
			newProductList=service.findNewProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获取最新产品信息
		List<category> AllCategory=null;
		try {
			AllCategory=service.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("AllCategory",AllCategory);
		request.setAttribute("HotProductList",HotProductList);
		request.setAttribute("newProductList", newProductList);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
		
	}
	
	
	//商品详情列表
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid=request.getParameter("cid");
		String currentPage=request.getParameter("currentPage");
		String pid=request.getParameter("pid");
		UserService service=new	UserService();
		product pro=null;
		try {
			pro = service.findProductInfoByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("product", pro);
				
		Cookie[] cookies = request.getCookies();
		String pids=pid;
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					pids=cookie.getValue();
					String[] split = pids.split("-");
					List<String> asList = Arrays.asList(split);
					LinkedList<String> list=new LinkedList<String>(asList);
					if(list.contains(pid)) {
						list.remove(pid);
						list.addFirst(pid);
					}else {
						list.addFirst(pid);
					}
					StringBuffer sb=new StringBuffer();
					for (int i = 0; i < list.size()&&i<7; i++) {
						sb.append(list.get(i));
						sb.append("-");
					}
					  pids = sb.substring(0,sb.length()-1);
				}
			}
		}
		Cookie cook_pids=new Cookie("pids",pids);
		response.addCookie(cook_pids);
		
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);		
	}
	
	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid=request.getParameter("cid");
		String currentPageStr=request.getParameter("currentPage");
		if(currentPageStr==null)currentPageStr="1";
		int currentPage=Integer.parseInt(currentPageStr);	
		int currentCount=12;
		UserService service=new UserService();
		PageBean<product> pageBean=service.findProductListByCid(cid,currentPage,currentCount);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		List<product> historyList=new ArrayList<product>();
		String pids="";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					pids=cookie.getValue();
					String[] split = pids.split("-");
					for (String pid : split) {
						product product =null;
						try {
							product = service.findProductInfoByPid(pid);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						historyList.add(product);						
					}
				}
			}
		}
		request.setAttribute("historyList", historyList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);

	}
	
	
}
