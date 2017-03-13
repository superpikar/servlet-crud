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
	
	<%@ include file="_header-admin.jsp" %>
	<jsp:invoke fragment="header"/>	
</head>
<body>
	<nav class="nav has-shadow">
		<div class="container">
	  	<div class="nav-left">
	    	<a class="nav-item" href="${pageContext.request.contextPath}/admin">
	    		<h1 class="title">Pikar CMS</h1>
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
    			<b>${sessionScope.user.username}(<i>${sessionScope.user.role}</i>)</b>
    		</a>
    		<div class="nav-item" >
    			<a href="<%=request.getContextPath()%>/admin?action=logout" class="button is-danger is-outlined">
      				Logout
      			</a>
    		</div>
  		</div>
		</div>
	</nav>
	<div id="app" class="container">
		<div class="columns">
			<div class="column is-2 is-fullheight">	
				<aside data-accordion-group class="menu">
					<c:if test="${sessionScope.cleanURI=='admin'}">
					<c:set var="isOpenClass" value="open"></c:set>
					</c:if>
					<div data-accordion class="${isOpenClass}">
						<p data-control class="menu-label">General</p>
						<ul data-content class="menu-list">
							<li>
								<a class="${sessionScope.cleanURI=='admin'?'is-active':''}" href="<%=request.getContextPath()%>/admin"><i class="fa fa-dashboard"></i> Dashboard</a>
							</li>
						</ul>					
					</div>
				<c:if test="${sessionScope.user.role=='admin' || sessionScope.user.role=='editor'}">
					<c:if test="${sessionScope.cleanURI=='admin/news'||sessionScope.cleanURI=='admin/news?action=add'||sessionScope.cleanURI=='admin/news?action=trash'}">
					<c:set var="isOpenClass2" value="open"></c:set>
					</c:if>
					<div data-accordion class="${isOpenClass2}">
						<p data-control class="menu-label">News</p>
						<ul data-content class="menu-list">
							<li>
								<a class="${sessionScope.cleanURI=='admin/news'?'is-active':''}" href="<%=request.getContextPath()%>/admin/news"><i class="fa fa-file"></i> View News</a>
							</li>
							<li>
								<a class="${sessionScope.cleanURI=='admin/news?action=add'?'is-active':''}" href="<%=request.getContextPath()%>/admin/news?action=add"><i class="fa fa-pencil-square-o"></i> Add News</a>
							</li>
							<li>
								<a class="${sessionScope.cleanURI=='admin/news?action=trash'?'is-active':''}" href="<%=request.getContextPath()%>/admin/news?action=trash"><i class="fa fa-trash"></i> Deleted News</a>
							</li>
						</ul>
					</div>
				</c:if>
				<c:if test="${sessionScope.user.role=='admin'}">
					<c:if test="${sessionScope.cleanURI=='admin/user'||sessionScope.cleanURI=='admin/user?action=add'||sessionScope.cleanURI=='admin/user?action=trash'}">
					<c:set var="isOpenClass3" value="open"></c:set>
					</c:if>
					<div data-accordion class="${isOpenClass3}">
						<p data-control class="menu-label">User</p>
						<ul data-content class="menu-list">
							<li>
								<a class="${sessionScope.cleanURI=='admin/user'?'is-active':''}" href="<%=request.getContextPath()%>/admin/user"><i class="fa fa-users"></i> View Users</a>
							</li>
							<li>
								<a class="${sessionScope.cleanURI=='admin/user?action=add'?'is-active':''}" href="<%=request.getContextPath()%>/admin/user?action=add"><i class="fa fa-pencil-square-o"></i> Add User</a>
							</li>
							<li>
								<a class="${sessionScope.cleanURI=='admin/user?action=trash'?'is-active':''}" href="<%=request.getContextPath()%>/admin/user?action=trash"><i class="fa fa-trash"></i> Deleted User</a>
							</li>
						</ul>
					</div>
				</c:if>
				<c:if test="${sessionScope.user.role=='admin' || sessionScope.user.role=='editor'}">
					<c:if test="${sessionScope.cleanURI=='admin/files'}">
					<c:set var="isOpenClass4" value="open"></c:set>
					</c:if>
					<div data-accordion class="${isOpenClass4}">
						<p data-control class="menu-label">Files</p>
						<ul data-content class="menu-list">
							<li>
								<a class="${sessionScope.cleanURI=='admin/files'?'is-active':''}" href="<%=request.getContextPath()%>/admin/files"><i class="fa fa-folder"></i> View Files</a>
							</li>
						</ul>
					</div>
				</c:if>
				</aside>
			</div>
			<div class="column is-10">
				<jsp:doBody/>
			</div>
		</div>
	</div>
	<%@ include file="_footer-admin.jsp" %>
	<jsp:invoke fragment="footer"/>	
	<script>
		$('[data-accordion]').accordion({
		    "transitionSpeed": 400
		});
	</script>
</body>
</html>