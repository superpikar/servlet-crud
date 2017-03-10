<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${requestScope.comments}" var="comment">
<div id="comment-${comment.id}" class="blog-comment media" data-id="${comment.id}" data-lineage="${comment.lineage}" style="padding-left: ${comment.level*78}px">
  <figure class="media-left">
    <p class="image is-64x64">
      <img src="http://bulma.io/images/placeholders/128x128.png">
    </p>
  </figure>
  <div class="media-content">
    <div class="content">
      <p>
        <strong>${comment.user.username}</strong> 
        <small>${comment.user.email}</small> 
        <small>${comment.registerDate}</small>
        <p>${comment.comment}</p>
      </p>
    </div>
   	<c:if test="${sessionScope.user==null}">
   		<small>Please <a href="${pageContext.request.contextPath}/admin/login?from=${pageContext.request.contextPath}/news?id=${post.id}">login</a> to reply a comment</small>
   	</c:if>
   	<c:if test="${sessionScope.user!=null}">
      	<a class="button is-light button-reply" onclick="replyComment('${comment.id}')">
        	<span class="icon is-small"><i class="fa fa-reply"></i></span>
        	<span> Reply</span>
      	</a>
   	</c:if>
  </div>
</div>
</c:forEach>