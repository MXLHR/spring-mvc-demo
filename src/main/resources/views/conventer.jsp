<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="asserts/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
function req(){
	$.ajax({
		url:"convert",
		data:"1-xianlei",
		type:"POST",
		contentType:"application/x-wisely",
		success:function(data){
			$("#resp").html(data)
		}
	});
}
</script>
</head>
<body>
<div id="resp">
	<input type="button" onclick="req();" value="请求"/>
</div>
</body>
</html>