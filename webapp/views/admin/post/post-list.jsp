<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>

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
		<c:import url="../shared/_search.jsp">
	  		<c:param name="formActionTo" value="/admin/news" />
	  		<c:param name="addRouteTo" value="/admin/news?action=add" />
	  		<c:param name="addButtonText" value="Add News" />
		</c:import>
		
		<c:import url="../shared/_sort.jsp">
	  		<c:param name="formActionTo" value="/admin/news" />
		</c:import>
		
		<table class="table">
			<thead>
				<tr>
					<th>Title</th>
					<th></th>
					<th>Summary</th>
					<th>Reg. Date</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${requestScope.paginations.totalRows == 0}">
				<tr>
					<td colspan="5" class="has-text-centered">
						Sorry, no data at the moment.
					</td>
				</tr>
			</c:if>
			<c:if test="${requestScope.paginations.totalRows > 0}">					
				<c:forEach items="${requestScope.posts}" var="post">
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/news?action=edit&id=${post.id}">${post.title}</a></td>
					<td>
						<figure class="image is-64x64">
							<c:choose>
								<c:when test="${not empty post.image}">
									<img src="${pageContext.request.contextPath}/files/${post.image}" alt="" />
								</c:when>
								<c:otherwise>
									<img src="http://placehold.it/64x64?text=no-image" alt="" />
								</c:otherwise>
							</c:choose>
						</figure>
					</td>
					<td>${post.summary}</td>
					<td>
						${post.registerDate}
					</td>
					<td>
					<c:choose>
						<c:when test="${requestScope.action == 'list' || requestScope.action=='delete' }">
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
						<c:when test="${requestScope.action == 'trash' || requestScope.action=='restore' }">
						<p class="control">
						  	<a class="button" href="#" v-on:click="confirmRestore(${post.id}, '${post.title}')">
						    	<span class="icon is-small">
						      		<i class="fa fa-undo"></i>
						    	</span>
						  	</a>
						</p>
						</c:when>
					</c:choose>
					</td>
				</tr>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
	  	
	  	<c:import url="../shared/_pagination.jsp">
	  		<c:param name="routeTo" value="/admin/news" />
		  	<c:param name="queryString" value="${requestScope.searchQueryString}${requestScope.sortQueryString}"/>
		</c:import>
	
	</jsp:body>
</t:layout-admin>