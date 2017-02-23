<%@tag description="Complex Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><jsp:invoke fragment="title" /></title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.3.1/css/bulma.min.css"></link>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"></link>
	<jsp:invoke fragment="header"/>	
</head>
<body>
	<nav class="nav has-shadow">
		<div class="container">
	  	<div class="nav-left">
	    	<a class="nav-item">
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
    			Profile
    		</a>
    		<a class="nav-item" href="<%=request.getContextPath()%>/admin?action=logout">
      			Logout
    		</a>
  		</div>
		</div>
	</nav>
	<div id="app" class="container">
		<div class="columns">
			<div class="column is-2 is-fullheight">
				<aside class="menu">
					<p class="menu-label">General</p>
					<ul class="menu-list">
						<li>
							<a href="<%=request.getContextPath()%>/admin"><i class="fa fa-dashboard"></i> Dashboard</a>
						</li>
					</ul>
					<p class="menu-label">News</p>
					<ul class="menu-list">
						<li>
							<a href="<%=request.getContextPath()%>/admin/news"><i class="fa fa-file"></i> View News</a>
						</li>
						<li>
							<a href="<%=request.getContextPath()%>/admin/news?action=add"><i class="fa fa-pencil-square-o"></i> Add News</a>
						</li>
						<li>
							<a href="<%=request.getContextPath()%>/admin/news?action=trash"><i class="fa fa-trash"></i> Deleted News</a>
						</li>
					</ul>
					<p class="menu-label">User</p>
					<ul class="menu-list">
						<li>
							<a href="<%=request.getContextPath()%>/admin/user"><i class="fa fa-users"></i> View Users</a>
						</li>
						<li>
							<a href="<%=request.getContextPath()%>/admin/user?action=add"><i class="fa fa-pencil-square-o"></i> Add User</a>
						</li>
						<li>
							<a href="<%=request.getContextPath()%>/admin/user?action=trash"><i class="fa fa-trash"></i> Deleted User</a>
						</li>
					</ul>
				</aside>
			</div>
			<div class="column is-10">
				<jsp:doBody/>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.4/lodash.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>		
	<jsp:invoke fragment="footer"/>	
</body>
</html>