<%@tag description="Complex Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><jsp:invoke fragment="title" /></title>
	
	<%@ include file="_header-client.jsp" %>
	<jsp:invoke fragment="header"/>	
</head>
<body>
	<nav class="nav has-shadow">
		<div class="container">
	  	<div class="nav-left">
	    	<a class="nav-item" href="${pageContext.request.contextPath}">
	    		<h1 class="title">My Blog</h1>
	      		<!-- <img src="http://bulma.io/images/bulma-logo.png" alt="Bulma logo"> -->
	    	</a>
	  	</div>
  		<!-- This "nav-toggle" hamburger menu is only visible on mobile -->
  		<!-- You need JavaScript to toggle the "is-active" class on "nav-menu" -->
	  	<span class="nav-toggle">
	    	<span></span>
	    	<span></span>
	    	<span></span>
	  	</span>

  		<!-- This "nav-menu" is hidden on mobile -->
  		<!-- Add the modifier "is-active" to display it on mobile -->
  		<div class="nav-right nav-menu">
    		<a class="nav-item" href="#">
    			Menu 1
    		</a>
    		<a class="nav-item" href="#">
    			Menu 2
    		</a>
  		</div>
		</div>
	</nav>
	<div id="app" class="container">
		<jsp:doBody/>
	</div>
	<%@ include file="_footer-client.jsp" %>
	<jsp:invoke fragment="footer"/>	
</body>
</html>