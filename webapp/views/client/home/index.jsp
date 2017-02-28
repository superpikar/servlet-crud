<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-client>
	<jsp:attribute name="title">
		Cras feugiat euismod sem accumsan ultrices.
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title has-text-centered">
			Cras feugiat euismod sem accumsan ultrices.
		</h1>
		<div class="columns is-multiline blog-posts">
		<c:forEach items="${requestScope.posts}" var="post">
			<div class="column is-full-desktop blog-post">
				<p class="blog-timestamp">${post.modificationDate}</p>
				<h1 class="blog-title">${post.title}</h1>
				<div class="blog-summary">
					${post.content}
				</div>
			</div>
		</c:forEach>
		</div>
	</jsp:body>
</t:layout-client>