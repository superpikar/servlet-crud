<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<t:layout-client>
	<jsp:attribute name="title">
		${requestScope.post.title }
	</jsp:attribute>
	
	<jsp:body>
		<div class="blog-posts">
			<div class="blog-post">
				<p class="blog-timestamp">
				<c:choose>
					<c:when test="${not empty requestScope.post.modificationDate}">${requestScope.post.modificationDate}</c:when>
					<c:otherwise>${requestScope.post.registerDate}</c:otherwise>
				</c:choose>
				</p>
				<h1 class="blog-title">${requestScope.post.title}</h1>
				<div class="blog-summary">
					${requestScope.post.content}
				</div>
				<hr />
				
				<div class="blog-comments">
					<c:set var="commentsCount" value="${fn:length(requestScope.comments)}"></c:set>
					<h3 class="title is-3">Comments (${commentsCount} comments)</h3>
					
					<c:if test="${sessionScope.user==null}">
					<div class="message is-danger">
						<div class="message-body">
							<%-- redirect to page after login, credit : http://stackoverflow.com/questions/1921230/redirect-back-to-a-page-after-a-login --%>
							<p>Please <a href="${pageContext.request.contextPath}/admin/login?from=${pageContext.request.contextPath}/news?id=${post.id}">login</a> first if you want to write a comment</p>
						</div>
					</div>
					</c:if>
					<c:if test="${sessionScope.user!=null}">
					<form class="blog-comment media" method="post" action="${pageContext.request.contextPath}/news">
						<figure class="media-left">
						  <p class="image is-64x64">
						    <img src="http://bulma.io/images/placeholders/128x128.png">
						  </p>
						</figure>
						<div class="media-content">
							<p>
								<strong>${sessionScope.user.username}</strong> <small>${sessionScope.user.email}</small> 
							</p>
						  <p class="control">
						    <textarea class="textarea" name="comment" placeholder="Add a comment..."></textarea>
						    </p>
						    <p class="control">
						    	<input type="hidden" name="channel" value="post"/>
						    	<input type="hidden" name="channelId" value="${requestScope.post.id}"/>
						    	<input type="hidden" name="parentId"/>
						      <button class="button">Post comment</button>
						    </p>
						  </div>
					</form>
					</c:if>
					
					<hr />
					
					<c:if test="${commentsCount == 0 }">
					<p class="has-text-centered">
						This post has no comment.
					</p>
					</c:if>
					<c:if test="${commentsCount > 0 }">
						<c:forEach items="${requestScope.comments}" var="comment">
						
						<c:import url="../shared/_comment.jsp">
							<c:param name="id" value="${comment.id}"></c:param>
							<c:param name="username" value="${comment.user.username}"></c:param>
							<c:param name="email" value="${comment.user.email}"></c:param>
							<c:param name="comment" value="${comment.comment}"></c:param>
							<c:param name="registerDate" value="${comment.registerDate}"></c:param>
						</c:import>
					
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
	</jsp:body>
</t:layout-client>