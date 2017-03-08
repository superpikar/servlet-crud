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
				<hr />
				
				<div class="blog-comments">
					<h3 class="title is-3">Comments (3 comments)</h3>
					
					<c:if test="${sessionScope.user==null}">
					<div class="message is-danger">
						<div class="message-body">
							<p>Please <a href="#">login</a> first if you want to write a comment</p>
						</div>
					</div>
					</c:if>
					<c:if test="${sessionScope.user!=null}">
					<div class="blog-comment media">
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
						    <textarea class="textarea" placeholder="Add a comment..."></textarea>
						    </p>
						    <p class="control">
						      <button class="button">Post comment</button>
						    </p>
						  </div>
					</div>
					</c:if>
					
					<c:import url="../shared/_comment.jsp"></c:import>
					<c:import url="../shared/_comment.jsp"></c:import>
					<c:import url="../shared/_comment.jsp"></c:import>
				</div>
			</div>
		</div>
	</jsp:body>
</t:layout-client>