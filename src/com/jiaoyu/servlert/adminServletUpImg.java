package com.jiaoyu.servlert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.jiaoyu.domain.category;
import com.jiaoyu.domain.product;
import com.jiaoyu.server.adminService;

public class adminServletUpImg extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		product product=new product();
		
		try {			
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem> parseRequest = upload.parseRequest(request);
			if(parseRequest!=null) {
				for (FileItem fileItem : parseRequest) {
					boolean isFile = fileItem.isFormField();
					if(isFile) {
						String fieldName = fileItem.getFieldName();
						String fieldValue = fileItem.getString("UTF-8");
						map.put(fieldName, fieldValue);						
					}else{
						String  myname=fileItem.getName();
						if(myname!=null&&myname!="") {
							System.out.println(myname);
							String fileName=myname.substring(myname.lastIndexOf("\\")+1);
							System.out.println(fileName);
							String path=this.getServletContext().getRealPath("products/1");
							System.out.println(path);
							map.put("pimage", "products/1/"+fileName);
							InputStream in = fileItem.getInputStream();
							OutputStream out =  new FileOutputStream(path+"/"+fileName);
							IOUtils.copy(in, out);
							in.close();
							out.close();	
						}
						
											
					}
				}	
			}
			System.out.println(map);
			BeanUtils.populate(product, map);
			System.out.println(product.getMarket_price()+"/"+product.getPimage());
			Date now=new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(dateFormat.format(now));		
			product.setPdate(date);
			product.setPflag(0);
			category category=new category();
			String cid=(String) map.get("cid");
			category.setCid(cid);
			product.setCategory(category);
			adminService service=new adminService();
			if(product!=null) {
				service.findPoductBypid(product);
			}
			
			response.sendRedirect(request.getContextPath()+"/adminServlet?method=showAllProduct");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
}
