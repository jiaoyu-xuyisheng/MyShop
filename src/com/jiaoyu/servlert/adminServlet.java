package com.jiaoyu.servlert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.jiaoyu.server.adminService;
import com.google.gson.Gson;
import com.jiaoyu.domain.category;
import com.jiaoyu.domain.orders;
import com.jiaoyu.domain.product;
import com.jiaoyu.domain.user;



public class adminServlet extends BaseServlet {
	
	
	public void showOneOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid=request.getParameter("oid");
		adminService serv=new adminService();
		List<Map<String,Object>> mapList=serv.showOneOrderByOid(oid);
		Gson gson=new Gson();
		System.out.println(mapList);
		String myList=gson.toJson(mapList);
		response.setContentType("text/html; charset=UTF-8");
		System.out.println(myList);
		response.getWriter().write(myList);

		
	}
	
	//查找所有订单！！
	public void showAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		adminService serv=new adminService();
		List<orders> orderList=serv.findAllOrder();
		session.setAttribute("orderList", orderList);
		System.out.println(orderList);
		response.sendRedirect(request.getContextPath()+"/admin/order/list.jsp");		
	}
	
	public void addCategoryToList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adminService serv=new adminService();
		category category=new category();
		String cname=request.getParameter("cname");
		String cid=UUID.randomUUID().toString();
		category.setCid(cid);
		category.setCname(cname);
		serv.addCategory(category);
		response.sendRedirect(request.getContextPath()+"/adminServlet?method=showCategory");
	}
	
	public void delCategoryAtList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid=request.getParameter("cid");
		adminService serv=new adminService();
		serv.delCategoryByPid(cid);
		response.sendRedirect(request.getContextPath()+"/adminServlet?method=showCategory");	
	}
	public void delProductAtList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid=request.getParameter("pid");
		adminService serv=new adminService();
		serv.delProductByPid(pid);
		response.sendRedirect(request.getContextPath()+"/adminServlet?method=showAllProduct");		
	}
		
	
	//修改分类;
	public void editCategoryToList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("utf-8");
			Map<String, String[]> map = request.getParameterMap();
			category category=new category();
			BeanUtils.populate(category, map);
			System.out.println(category.getCid()+"/"+category.getCname());
			adminService serv=new adminService();
			serv.updateCategoryBycid(category);
			response.sendRedirect(request.getContextPath()+"/adminServlet?method=showCategory");
			

		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	//点击修改分类！！
	public void editCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			HttpSession session = request.getSession();
			request.setCharacterEncoding("utf-8");
			Map<String, String[]> map = request.getParameterMap();
			category category=new category();
			BeanUtils.populate(category, map);
			session.setAttribute("mycategory", category);
			response.sendRedirect(request.getContextPath()+"/admin/category/edit.jsp");			
		} catch (IllegalAccessException |InvocationTargetException e) {
		
			e.printStackTrace();
		} 
	}
	
	//点击修改产品！！！
	public void adminProductEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		adminService serv=new adminService();
		String pid=request.getParameter("pid");
		product product= serv.findPoductBypid(pid);
		session.setAttribute("editProduct", product);
		response.sendRedirect(request.getContextPath()+"/admin/product/edit.jsp");
	
	}
	
	//通过json得到产品分类类表
	public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		adminService serv=new adminService();
		List<category> categoryList= serv.findAllCategory();
		Gson gson=new Gson();
		String myjson=gson.toJson(categoryList);
		response.getWriter().write(myjson);
	
	}
	
	
	//通过json显示category
	public void showCategoryByJSON(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		adminService serv=new adminService();
		List<category> categoryList= serv.findAllCategory();
		Gson gson=new Gson();
		String myjson=gson.toJson(categoryList);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(myjson);
	
	}
	
	
	//显示所有产品
	public void showAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		adminService serv=new adminService();
		List<product> productList= serv.findAllProduct();
		session.setAttribute("productList", productList);
		response.sendRedirect(request.getContextPath()+"/admin/product/list.jsp");		
	}
	
	//显示所有分类
	public void showCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		adminService serv=new adminService();
		List<category> categoryList= serv.findAllCategory();
		session.setAttribute("categoryList", categoryList);
	
		response.sendRedirect(request.getContextPath()+"/admin/category/list.jsp");
	}
	
	//后台登录！！！
	public void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username=request.getParameter("username");
		String password =request.getParameter("password");
		adminService serv=new adminService();
		if(username!=null&&password!=null) {
			user use=serv.login(username,password);
			if(use==null) {
				response.sendRedirect(request.getContextPath()+"/admin/index.jsp");
				return;
			}else {
				session.setAttribute("use", use);
				response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
				return;
			}			
		}else {
			response.sendRedirect(request.getContextPath()+"/admin/index.jsp");
			return;
		}
		
	}



}
