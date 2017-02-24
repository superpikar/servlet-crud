<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">File Explorer</jsp:attribute>
	<jsp:attribute name="header">
		<style>
			.image{
				height: 150px;
				width: 100%;
			}
			.image.is-directory{
				padding: 25%;
				text-align: center;
			}
			.breadcrumbs {
			    box-shadow: 0 2px 3px rgba(10,10,10,.1), 0 0 0 1px rgba(10,10,10,.1);
			    display: block;
			    padding: 1rem;
			}
		</style>
	</jsp:attribute>
	<jsp:attribute name="footer">
		<script>
			var app = new Vue({
			  	el: '#app',
			  	methods: {
			  		
			  	}
			});			
		</script>
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title">File Explorer</h1>
		
		<div class="columns is-multiline">
			<div class="column is-12">
				<ul class="breadcrumb">
					<c:if test="${dir==''}">
					<li class="is-active">Files</li>
					</c:if>
					<c:if test="${dir!=''}">
					<li><a href="${pageContext.request.contextPath}/admin/files">Files</a></li>
					</c:if>
					
					<c:forEach items="${dirSplit}" var="folder">
						<c:if test="${folder.value!=dir}">
						<li><a href="${pageContext.request.contextPath}/admin/files?dir=${folder.value}">${folder.key}</a></li>
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
						<a href="${pageContext.request.contextPath}/admin/files?dir=${dir}${file.name}">
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
	</jsp:body>
</t:layout-admin>