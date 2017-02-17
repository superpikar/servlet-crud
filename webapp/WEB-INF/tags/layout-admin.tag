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
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"></link>
	<jsp:invoke fragment="header"/>	
</head>
<body>
	<div id="app" class="container">
		<div class="columns">
			<div class="column is-2 is-fullheight">
				<h1 class="title">Pikar CMS</h1>
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
	<jsp:invoke fragment="footer"/>	
</body>
</html>