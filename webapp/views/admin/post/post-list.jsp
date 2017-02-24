<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">Post List</jsp:attribute>
	
	<jsp:attribute name="footer">
		<script>
			var app = new Vue({
			  	el: '#app',
			  	methods: {
			  		confirmRestore: function(id, title) {
			  			SweetAlertWrapper.showWarning({
			  				title: 'Are you sure want to restore "'+title+'"',
			  				subtitle: "You will not be able to recover the file!",
			  				confirmButtonText: "Yes, restore it!",
			  				cancelButtonText: "No, cancel please",
			  				messageSuccessTitle: "Restored!",
	  					   	messageSuccessSubtitle: "Your file has been restored.",
	  					   	messageCancelTitle: "Cancelled",
	  					   	messageCancelSubtitle: "Your file is safe :)",
	  					   	forwardTo: "${pageContext.request.contextPath}/admin/news?action=restore&id="+id
			  			});
			  		},
				  	confirmDelete: function(id, title) {
				  		SweetAlertWrapper.showWarning({
			  				title: 'Are you sure want to delete "'+title+'"',
			  				subtitle: "You will not be able to recover the file!",
			  				confirmButtonText: "Yes, restore it!",
			  				cancelButtonText: "No, cancel please",
			  				messageSuccessTitle: "Deleted!",
	  					   	messageSuccessSubtitle: "Your file has been deleted.",
	  					   	messageCancelTitle: "Cancelled",
	  					   	messageCancelSubtitle: "Your file is safe :)",
	  					   	forwardTo: "${pageContext.request.contextPath}/admin/news?action=delete&id="+id
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
							<th></th>
							<th>Created</th>
							<th>Modification</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${posts}" var="post">
						<tr>
							<td><a href="${pageContext.request.contextPath}/admin/news?action=edit&id=${post.id}">${post.title}</a></td>
							<td>
								<figure class="image is-64x64">
									<c:choose>
										<c:when test="${not empty post.image}">
											<img src="${pageContext.request.contextPath}/files/${post.image}" alt="" />
										</c:when>
										<c:otherwise>
											<img src="http://placehold.it/64x64" alt="" />
										</c:otherwise>
									</c:choose>
								</figure>
							</td>
							<td>
								${post.registerUserId}<br/>
								${post.registerDate}
							</td>
							<td>
								${post.modificationUserId}<br/>
								${post.modificationDate}
							</td>
							<td>
							<c:choose>
								<c:when test="${action == null || action=='delete' }">
								<p class="control">
								  	<a class="button" href="${pageContext.request.contextPath}/admin/news?action=edit&id=${post.id}">
								    	<span class="icon is-small">
								      		<i class="fa fa-pencil-square-o"></i>
								    	</span>
								  	</a>
								  	<a class="button" href="#" v-on:click="confirmDelete(${post.id}, '${post.title}')">
								    	<span class="icon is-small">
								      		<i class="fa fa-times"></i>
								    	</span>
								  	</a>
								</p>
								</c:when>
								<c:when test="${action == 'trash' || action=='restore' }">
								<p class="control">
								  	<a class="button" href="#" v-on:click="confirmRestore(${post.id}, '${post.title}')">
								    	<span class="icon is-small" alt="restore">
								      		<i class="fa fa-undo"></i>
								    	</span>
								  	</a>
								</p>
								</c:when>
							</c:choose>
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