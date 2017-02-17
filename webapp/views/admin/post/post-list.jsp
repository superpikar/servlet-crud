<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">Post List</jsp:attribute>
	
	<jsp:attribute name="header">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/vex-js/3.0.0/css/vex.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/vex-js/3.0.0/css/vex-theme-plain.min.css" />
	</jsp:attribute>
	
	<jsp:attribute name="footer">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vex-js/3.0.0/js/vex.combined.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>
		<script>
			var app = new Vue({
			  	el: '#app',
			  	methods: {
				  	confirmDelete: function(ID, title) {
				  		vex.dialog.confirm({
				  		    message: 'Are you sure want to delete "'+title+'"',
				  		    className: 'vex-theme-plain', // Overwrites defaultOptions,
				  		    callback: function(value) {
				  		    	if (value) {
				  		    		window.location="${pageContext.request.contextPath}/admin/news?action=delete&id="+ID;
				  		            console.log('Successfully destroyed the planet.', '${pageContext.request.contextPath}/admin/news?action=delete&id='+ID)
				  		        } else {
				  		            console.log('Chicken.')
				  		        }
				  		    }
				  		}); 	
				  	}
			  	}
			});			
		</script>
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title">Post List</h1>
		<div class="columns">
			<div class="column is-9">
				<table class="table">
					<thead>
						<tr>
							<th>Title/Excerpt</th>
							<th>Author</th>
							<th>Created at</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${posts}" var="post">
						<tr>
							<td><a href="${pageContext.request.contextPath}/admin/news?action=edit&id=${post.ID}"><c:out value="${post.title}" /></a></td>
							<td><c:out value="${post.userID}" /></td>
							<td>
								<c:out value="${post.createdDate}" />
							</td>
							<td>
								<p class="control">
								  	<a class="button" href="${pageContext.request.contextPath}/admin/news?action=edit&id=${post.ID}">
								    	<span class="icon is-small">
								      		<i class="fa fa-pencil-square-o"></i>
								    	</span>
								  	</a>
								  	<a class="button" href="#" v-on:click="confirmDelete(${post.ID}, '${post.title}')">
								    	<span class="icon is-small">
								      		<i class="fa fa-times"></i>
								    	</span>
								  	</a>
								</p>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="column is-3">
				<div class="card">
					<header class="card-header">
				    	<p class="card-header-title">Actions</p>
				  	</header>
				  	<div class="card-content">
				    	<div class="content">
				    		<!-- get page context http://stackoverflow.com/questions/5850336/what-does-this-expression-language-pagecontext-request-contextpath-exactly-do -->
				    		<a href="${pageContext.request.contextPath}/admin/news?action=add" class="button is-primary">Add News</a>
				      	</div>
				    </div>
				</div>
			</div>
		</div>	
	
	</jsp:body>
</t:layout-admin>