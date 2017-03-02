<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-client>
	<jsp:attribute name="title">
		${post.title }
	</jsp:attribute>
	
	<jsp:body>
		<div class="blog-posts">
			<div class="blog-post">
				<p class="blog-timestamp">
				<c:choose>
					<c:when test="${not empty post.modificationDate}">${post.modificationDate}</c:when>
					<c:otherwise>${post.registerDate}</c:otherwise>
				</c:choose>
				</p>
				<h1 class="blog-title">${post.title}</h1>
				<div class="blog-summary">
					${post.content}
				</div>
			</div>
		</div>
	</jsp:body>
</t:layout-client>