<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>

<t:layout-client>
	<jsp:attribute name="title">
		Cras feugiat euismod sem accumsan ultrices.
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title has-text-centered">
			Cras feugiat euismod sem accumsan ultrices.
		</h1>
		<div class="blog-posts">
		<c:forEach items="${requestScope.posts}" var="post">
			<div class="columns blog-post">
				<div class="column is-4">
					<a href="${pageContext.request.contextPath}/news?id=${post.id}">
						<img src="${pageContext.request.contextPath}/files/${post.image}" alt="" />
					</a>
				</div>
				<div class="column is-8">
					<p class="blog-timestamp">
					<c:choose>
						<c:when test="${not empty post.modificationDate}">${post.modificationDate}</c:when>
						<c:otherwise>${post.registerDate}</c:otherwise>
					</c:choose>
					</p>
					<a href="${pageContext.request.contextPath}/news?id=${post.id}">
					<h1 class="blog-title">${post.title}</h1>
					</a>
					<div class="blog-summary">
						${post.summary}
						<p>
						<a href="${pageContext.request.contextPath}/news?id=${post.id}">.... read more</a>
						</p>
					</div>
				</div>
			</div>
		</c:forEach>
			<c:set var="firstData" value="${((requestScope.pageNumber-1)*requestScope.paginations.postPerPage) + 1}"></c:set>
			<c:set var="lastData" value="${firstData-1+fn:length(requestScope.posts)}"></c:set>
			Showing ${firstData}~${lastData} of ${requestScope.paginations.totalRows}
			<nav class="pagination">
				<ul class="pagination-list">
					<c:forEach items="${requestScope.paginations.paginations}" var="pagination">
				    <li>
				    	<c:if test="${requestScope.pageNumber==pagination}">
				    	<a class="pagination-link is-current" href="${pageContext.request.contextPath}/?page=${pagination}">${pagination}</a>
				    	</c:if>
				    	<c:if test="${requestScope.pageNumber!=pagination}">
				    	<a class="pagination-link" href="${pageContext.request.contextPath}/?page=${pagination}">${pagination}</a>
				    	</c:if>
				  	</li>
					</c:forEach>
				</ul>
			</nav>
		</div>		
	</jsp:body>
</t:layout-client>