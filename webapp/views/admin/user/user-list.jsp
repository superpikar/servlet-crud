<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">User List</jsp:attribute>
	
	<jsp:attribute name="footer">
		<script>
			var app = new Vue({
			  	el: '#app',
			  	methods: {
			  		confirmRestore: function(id, title) {
			  			swal({
				  		  title: 'Are you sure want to restore "'+title+'"',
				  		  text: "You will not be able to recover the file!",
				  		  type: "warning",
				  		  showCancelButton: true,
				  		  confirmButtonColor: "#DD6B55",
				  		  confirmButtonText: "Yes, restore it!",
				  		  cancelButtonText: "No, cancel plx!",
				  		  closeOnConfirm: false,
				  		  closeOnCancel: false
				  		},
				  		function(isConfirm){
				  			if (isConfirm) {
					  		  swal("Restored!", "Your file has been restored.", "success");
					  		  window.setTimeout(function(){
					  		  	window.location="${pageContext.request.contextPath}/admin/news?action=restore&id="+id;		  		            
					  		  }, 1000);
				  			}
				  			else{
				  				swal("Cancelled", "Your file is safe :)", "error");
				  			}
				  		});
			  		},
				  	confirmDelete: function(id, title) {
				  		swal({
				  		  title: 'Are you sure want to delete "'+title+'"',
				  		  text: "You will not be able to recover the file!",
				  		  type: "warning",
				  		  showCancelButton: true,
				  		  confirmButtonColor: "#DD6B55",
				  		  confirmButtonText: "Yes, delete it!",
				  		  cancelButtonText: "No, cancel plx!",
				  		  closeOnConfirm: false,
				  		  closeOnCancel: false
				  		},
				  		function(isConfirm){
				  		  if (isConfirm) {
				  		    swal("Deleted!", "Your file has been deleted.", "success");
				  		    window.setTimeout(function(){
				  		  		window.location="${pageContext.request.contextPath}/admin/news?action=delete&id="+id;		  		            
				  		    }, 1000)
				  		  } else {
				  		    swal("Cancelled", "Your file is safe :)", "error");
				  		  }
				  		});
				  	}
			  	}
			});			
		</script>
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title">User List</h1>
		<div class="columns">
			<div class="column is-9">
				<table class="table">
					<thead>
						<tr>
							<th>Username</th>
							<th></th>
							<th>Created</th>
							<th>Modification</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
						<tr>
							<td><a href="${pageContext.request.contextPath}/admin/user?action=edit&id=${user.id}">${user.username}</a></td>
							<td>
								<figure class="image is-64x64">
									<c:choose>
										<c:when test="${not empty user.image}">
											<img src="${pageContext.request.contextPath}/images/${user.image}" alt="" />
										</c:when>
										<c:otherwise>
											<img src="http://placehold.it/64x64" alt="" />
										</c:otherwise>
									</c:choose>
								</figure>
							</td>
							<td>
								${user.registerUserId}<br/>
								${user.registerDate}
							</td>
							<td>
								${user.modificationUserId}<br/>
								${user.modificationDate}
							</td>
							<td>
							<c:choose>
								<c:when test="${action == null || action=='delete' }">
								<p class="control">
								  	<a class="button" href="${pageContext.request.contextPath}/admin/user?action=edit&id=${user.id}">
								    	<span class="icon is-small">
								      		<i class="fa fa-pencil-square-o"></i>
								    	</span>
								  	</a>
								  	<a class="button" href="#" v-on:click="confirmDelete(${user.id}, '${user.username}')">
								    	<span class="icon is-small">
								      		<i class="fa fa-times"></i>
								    	</span>
								  	</a>
								</p>
								</c:when>
								<c:when test="${action == 'trash' || action=='restore' }">
								<p class="control">
								  	<a class="button" href="#" v-on:click="confirmRestore(${user.id}, '${user.username}')">
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
				    		<a href="${pageContext.request.contextPath}/admin/user?action=add" class="button is-primary">Add User</a>
				      	</div>
				    </div>
				</div>
			</div>
		</div>	
	</jsp:body>
</t:layout-admin>