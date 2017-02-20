<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">Post List</jsp:attribute>
	
	<jsp:attribute name="header">
		<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/vex-js/3.0.0/css/vex.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/vex-js/3.0.0/css/vex-theme-plain.min.css" /> -->
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
	</jsp:attribute>
	
	<jsp:attribute name="footer">
		<!-- <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vex-js/3.0.0/js/vex.combined.min.js"></script> -->
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>
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
			
						/* vex.dialog.confirm({
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
				  		}); */ 	
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
											<img src="${pageContext.request.contextPath}/images/${post.image}" alt="" />
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