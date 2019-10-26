package com.jiaoyu.servlert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.jiaoyu.domain.orders;
import com.jiaoyu.domain.user;
import com.jiaoyu.server.UserService;
import com.jiaoyu.util.MailUtils;

public class userServlet extends BaseServlet {
	
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			HttpSession session = request.getSession();
			session.removeAttribute("use");
			Cookie cook_username=new Cookie("cook_username","");
			Cookie cook_password=new Cookie("cook_password","");
			cook_username.setMaxAge(0);
			cook_password.setMaxAge(0);
			cook_username.setPath(request.getContextPath());
			cook_password.setPath(request.getContextPath());
			response.addCookie(cook_username);
			response.addCookie(cook_password);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			
		
	}
		
	//检查用户名是否存在
	public void checkUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username=request.getParameter("username");
		UserService service=new UserService();
		boolean isExist=false;
		try {
			isExist = service.isExist(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = "{\"isExist\":"+isExist+"}";
		response.getWriter().write(json);
	}
	
	
	//登录功能
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//这个转成中文编码！！如果没有这句就无法完成验证码验证！！！
				
		HttpSession session=request.getSession();	
		String checkcode=request.getParameter("checkCode");
		String code=(String) session.getAttribute("checkcode_session");
		//验证码提示
		if(!(code.equals(checkcode))) {
			if(checkcode==null) {
				request.setAttribute("msg","");	
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;//一定要写return;不然后面无法再写转发和重定向。
			}else{
				request.setAttribute("msg", checkcode);	
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			
		}
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			UserService service=new UserService();
			user use=null;
			try {
				use = service.login(username,password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(use!=null) {
				String autoLogin=request.getParameter("autoLogin");
				if(autoLogin!=null) {
					String code_username=URLEncoder.encode(username,"UTF-8");
					Cookie cook_username=new Cookie("cook_username",code_username);
					Cookie cook_password=new Cookie("cook_password",password);
					cook_username.setMaxAge(60*60*24*20);
					cook_password.setMaxAge(60*60*24*20);
					cook_username.setPath(request.getContextPath()+"/index.jsp");
					cook_password.setPath(request.getContextPath()+"/index.jsp");
					response.addCookie(cook_username);
					response.addCookie(cook_password);
					
				}
					session.setAttribute("use", use);
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				
			}else {
				request.setAttribute("msg", "用户名或密码错误");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}	
	}
	
	
	//注册功能
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		String checkCode=request.getParameter("checkCode");
		String checkcode=(String) session.getAttribute("checkcode_session");

		if(!(checkcode.equals(checkCode))) {
			if(checkcode==null||checkCode==null) {
				request.setAttribute("msg","");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			}else {
				request.setAttribute("msg", "验证码错误");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			}
			
		}
	
		//获得表单数据;
		Map<String, String[]> map=request.getParameterMap();
		user use=new user();
		try {
			ConvertUtils.register(new Converter() {

				@Override
				public Object convert(Class clazz, Object value) {
					//将string转成date
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse=format.parse(value.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);
			BeanUtils.populate(use, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		use.setUid(UUID.randomUUID().toString());
		use.setState(0);
		use.setBirthday(null);
		String activeCode=UUID.randomUUID().toString();
		use.setCode(activeCode);
		UserService service=new UserService();
		try {
			boolean isRegisterSuccess=service.regiest(use);
			if(isRegisterSuccess) {
				String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
						+ "<a href='http://localhost:8080/MyShop/myproductServlet?method=active&activeCode="+activeCode+"'>"
								+ "http://localhost:8080/MyShop/myproductServlet?method=active&activeCode="+activeCode+"</a>";
				try {
					MailUtils.sendMail(use.getEmail(), emailMsg);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
			}else {
				response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
