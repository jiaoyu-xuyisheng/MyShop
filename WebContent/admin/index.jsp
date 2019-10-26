<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>网上商城管理中心</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath }/css/general.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" />

<style type="text/css">
body { color: white;}
</style>
</head>
<body style="background: #278296">
<center></center>
<form method="post" action="${pageContext.request.contextPath }/adminServlet?method=adminLogin"  id="loginForm" >
  <table cellspacing="0" cellpadding="0" style="margin-top: 100px" align="center">
  <tr>
    <td style="padding-left: 50px">
      <table>
      <tr>
        <td>管理员姓名：</td>
        <td><input type="text" name="username" id="username" class="form-control"/></td>
      </tr>
      <tr>
        <td>管理员密码：</td>
        <td><input type="password" name="password" id="password" class="form-cotrol"/></td>
      </tr>
      <tr>
      <td>&nbsp;</td>
      <td>
      <input type="submit" value="进入管理中心" class="button" /></td></tr>
      </table>
    </td>
  </tr>
  </table>
</form>
<script type="text/javascript">
	$(function(){
		$("#loginForm").validate({
			rules:{
				"username":{//这个username是表单里的name
					"required":true
				},
				"password":{
					"required":true
				}
			},
			messages:{
				"username":{
					"required":"用户名不能为空！",//这是错误信息，
				},
				"password":{
					"required":"密码不能为空！",				
				}
			}		
		
		})
	})
	
	
</script>
</body>
</html>