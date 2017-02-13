<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%! int pageCount = 0;
	void addCount() {
		pageCount++;
	}
 %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Header and Footer Include</title>
</head>
<body>
	<center>
		<h2>Servlets and J2EE Web Tier</h2>
		<p><a href="http://jspbook.com">Book Support Site</a></p>
		<p><a href="<%=request.getContextPath()%>/ShowSource">Site Source Code</a></p>
	</center>
