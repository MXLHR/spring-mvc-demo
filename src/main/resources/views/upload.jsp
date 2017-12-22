<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
 	<h2>文件上传页面</h2>
 	<div class="upload">
 	<!-- 	<form action="upload" enctype="multipart/form-data" method="post">
 			<input type="file" name="file"/>
 			<input type="submit" value="上传"/>
 		</form> -->
 		<form name="upload" action="upload" method="post"  enctype="multipart/form-data">
		<h1>采用流的方式上传文件</h1>
		<input type="file" name="file">
		<input type="submit" value="upload"/>
		</form>
 	</div>
 	
</body>
</html>