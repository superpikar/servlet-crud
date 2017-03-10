<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<t:layout-client>
	<jsp:attribute name="title">
		${requestScope.post.title }
	</jsp:attribute>
	
	<jsp:attribute name="footer">
	<script type="text/javascript">
		var buttonCancelEl = $('#reply .button-cancel');
		
		function replyComment(commentId){
			var commentEl = $('#comment-'+commentId);
			
			buttonCancelEl.removeClass('is-hidden');
			buttonCancelEl.attr('onclick', 'cancelComment("'+commentId+'")');
			
			var replyEl = $('#reply').detach();
			replyEl.appendTo('#comment-'+commentId+' .media-content');
			
			$('#reply [name=parentId]').attr('value', commentEl.data('id'));
			$('#reply [name=lineage]').attr('value', commentEl.data('lineage'));
		}
		
		function cancelComment(commentId){
			buttonCancelEl.addClass('is-hidden');
			buttonCancelEl.removeAttr('onclick');
			
			var replyEl = $('#reply').detach();
			replyEl.appendTo('#reply-wrapper');
			
			$('#reply [name=parentId]').removeAttr('value');
			$('#reply [name=lineage]').removeAttr('value');
		}
	</script>
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
					<div id="reply-wrapper">
						<div id="reply">
							<div class="level">
								<div class="level-left">
									<div class="level-item">
										<h4 class="title is-4">Leave a reply</h4>
									</div>
								</div>
								<div class="level-right">
									<div class="level-item">
										<a href="#" class="button is-small is-hidden button-cancel">cancel reply</a>
									</div>
								</div>
							</div>					
							<form class="blog-comment media" method="post" action="${pageContext.request.contextPath}/news">
								<figure class="media-left">
								  <p class="image is-64x64">
								    <img src="http://bulma.io/images/placeholders/128x128.png">
								  </p>
								</figure>
								<div class="media-content">
								  <p class="control">
								    <textarea class="textarea" name="comment" placeholder="Add a comment..."></textarea>
								    </p>
								    <p class="control">
								    	<input type="hidden" name="channel" value="post"/>
								    	<input type="hidden" name="channelId" value="${requestScope.post.id}"/>
								    	<input type="hidden" name="parentId"/>
								    	<input type="hidden" name="lineage"/>
								      	<button class="button">Post comment</button>
								      	as <strong>${sessionScope.user.username}</strong> <small>${sessionScope.user.email}</small> 
								    </p>
								  </div>
							</form>
						</div>
					</div>
					</c:if>
					
					<hr />
					
					<c:if test="${commentsCount == 0 }">
					<p class="has-text-centered">
						This post has no comment.
					</p>
					</c:if>
					<c:if test="${commentsCount > 0 }">
						<c:import url="../shared/_comments.jsp"></c:import>
					</c:if>
				</div>
			</div>
		</div>
	</jsp:body>
</t:layout-client>