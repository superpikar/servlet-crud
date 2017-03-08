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
		<c:if test="${requestScope.paginations.totalRows == 0}">
			<p class="has-text-centered">Sorry, no data at the moment.</p>
		</c:if>
		<c:if test="${requestScope.paginations.totalRows > 0}">
			<c:forEach items="${requestScope.posts}" var="post">
			<div class="columns blog-post">
				<div class="column is-4">
					<a href="${pageContext.request.contextPath}/news?id=${post.id}">
					<c:choose>
						<c:when test="${not empty post.image}">
						<img src="${pageContext.request.contextPath}/files/${post.image}" alt="" />
						</c:when>
						<c:otherwise>
						<img src="http://placehold.it/350x230?text=no-image" alt="" />
						</c:otherwise>
					</c:choose>
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
		</c:if>
		<c:import url="../shared/_pagination.jsp">
		  <c:param name="itemsLength" value="${fn:length(requestScope.posts)}"/>
		  <c:param name="routeTo" value=""/>
		  <c:param name="queryString" value=""/>
		</c:import>
		</div>		
	</jsp:body>
</t:layout-client>