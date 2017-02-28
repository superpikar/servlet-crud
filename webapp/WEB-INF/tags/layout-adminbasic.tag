<%@tag description="Complex Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><jsp:invoke fragment="title" /></title>
	
	<!-- use JSP include because when using c:import cannot be rendered on iframe -->
	<%@ include file="_header-admin.jsp" %>
	<jsp:invoke fragment="header"/>	
</head>
<body>
	<jsp:doBody/>
	<%@ include file="_footer-admin.jsp" %>
	<jsp:invoke fragment="footer"/>	
</body>
</html>