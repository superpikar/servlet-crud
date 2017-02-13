<%-- tutorial http://stackoverflow.com/questions/1296235/jsp-tricks-to-make-templating-easier --%>

<%@tag description="Complex Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="title" fragment="true" %>

<html>
<head>
	<title>
		<jsp:invoke fragment="title"/>
	</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.3.1/css/bulma.min.css"></link>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"></link>
	<jsp:invoke fragment="header"/>
	<jsp:invoke fragment="footer"/>
</head>
<body>
	<div id="app" class="container">
		<jsp:doBody/>	
	</div>
</body>
</html>