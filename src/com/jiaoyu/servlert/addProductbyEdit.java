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
import javax.servlet.ServletInputStream;
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

public class addProductbyEdit extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Map<String,Object> map=new HashMap<String,Object>();
		product product=new product();
		
		try {
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem> fileItems = upload.parseRequest(request);
			if(fileItems!=null) {
				for (FileItem Item : fileItems) {
					boolean isForm = Item.isFormField();
					if(isForm) {
						String fieldName=Item.getFieldName();
						String fieldValue=Item.getString("UTF-8");
						map.put(fieldName, fieldValue);
					}else {
							String fileName=Item.getName();
							fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
							System.out.println(fileName);
							map.put("pimage", "products/1/"+fileName);
							String path=this.getServletContext().getRealPath("products/1");
							System.out.println(path);
							InputStream in = Item.getInputStream();
							OutputStream out =new FileOutputStream(path+"/"+fileName);
							IOUtils.copy(in, out);
							
							in.close();
							out.close();
						
					}
					
				}
			}
			System.out.println(map);
			BeanUtils.populate(product, map);
			product.setPid(UUID.randomUUID().toString());
			String cid=(String)map.get("cid");
			category category=new category();
			category.setCid(cid);
			product.setCategory(category);
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			date=sdf.parse(sdf.format(date));
			product.setPdate(date);
			product.setPflag(0);
			adminService service=new adminService();
			if(product!=null) {
				service.addProductByEdit(product);
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
