<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 class="title">File Explorer</h1>
<div class="columns is-multiline file-explorer">
	<div class="column is-12">
		<ul class="breadcrumb">
			<c:if test="${dir==''}">
			<li class="is-active">Files</li>
			</c:if>
			<c:if test="${dir!=''}">
			<li><a href="${pageContext.request.contextPath}/admin/files?layout=${requestScope.layout}">Files</a></li>
			</c:if>
			
			<c:forEach items="${dirSplit}" var="folder">
				<c:if test="${folder.value!=dir}">
				<li><a href="${pageContext.request.contextPath}/admin/files?layout=${requestScope.layout}&dir=${folder.value}">${folder.key}</a></li>
				</c:if>
				<c:if test="${folder.value==dir}">
				<li class="is-active">${folder.key}</li>
				</c:if>
			 </c:forEach>					
		</ul>
	</div>
<c:if test="${not empty files}">
<c:forEach items="${files}" var="file">
	<div class="column is-3">	
		<div class="card">
		  	<div class="card-image">
			<c:choose>
				<c:when test="${file.directory}">
				<a href="${pageContext.request.contextPath}/admin/files?layout=${requestScope.layout}&dir=${dir}${file.name}">
				  	<div class="image is-directory">	
				      	<i class="fa fa-5x fa-folder"></i>				    	
				  	</div>
				</a>
			  	</c:when>
			  	<c:otherwise>
		    	<figure class="image" style="background: url('${pageContext.request.contextPath}/files/${file.name}'); background-size: cover;">
		     	
		    	</figure>
			  	</c:otherwise>
			</c:choose>	
		  	</div>
	    	<div class="card-content">
	    		<div class="content">
	  				${file.name}
	    		</div>
	    	</div>
		</div>				
	</div>
</c:forEach>		
</c:if>
<c:if test="${empty files}">
<div class="column">
	<article class="message is-warning is-fullwidth">
  		<div class="message-body">
			<h3 class="title">Sorry, no files here.</h3>	
  		</div>
  	</article>
</div>
</c:if>
</div>