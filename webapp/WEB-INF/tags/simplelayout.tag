<%-- tutorial http://stackoverflow.com/questions/1296235/jsp-tricks-to-make-templating-easier --%>

<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Simple Template</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.3.1/css/bulma.min.css"></link>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"></link
</head>
<body>
	<div id="app" class="container">
		<jsp:doBody/>	
	</div>
</body>
</html>