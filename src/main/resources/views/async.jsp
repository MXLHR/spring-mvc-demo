<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="asserts/js/jquery-3.2.1.js" ></script>
<script type="text/javascript">
deferred();
function deferred(){
	$.get('defer',function(data){
		console.info(data);
		deferred();
	});
}
/*
 * 此处的代码使用的是Jquery的Ajax请求，所以没有浏览器兼容性问题。
 * 1.页面打开就向后台发请求，
   2.在控制台输出服务器推送的数据。
   3.一次请求完成后再向后台发送请求
   
   在MyMvcConfig上开始计划任务的支持，使用@EnableScheduling
 */
</script>
</head>
<body>
</body>
</html>