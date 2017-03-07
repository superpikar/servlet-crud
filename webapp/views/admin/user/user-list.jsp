<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>

<t:layout-admin>
	<jsp:attribute name="title">User List</jsp:attribute>
	
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
	  					   	forwardTo: "${pageContext.request.contextPath}/admin/user?action=restore&id="+id
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
	  					   	forwardTo: "${pageContext.request.contextPath}/admin/user?action=delete&id="+id
			  			});
				  	}
			  	}
			});			
		</script>
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title">User List</h1>
		<div class="level">
			<div class="level-left">
				<div class="level-item">
					<form action="${pageContext.request.contextPath}/admin/user">
						<div class="control is-grouped">
						    <p class="control is-expanded">
						      	<span class="select">
								    <select name="condition">
								    <c:forEach items="${requestScope.searchConditions}" var="val">
								    	<c:if test="${val.key==requestScope.condition}">
								      	<option selected value="${val.key}">${val.value}</option>
								    	</c:if>
								    	<c:if test="${val.key!=requestScope.condition}">
								      	<option value="${val.key}">${val.value}</option>
								    	</c:if>							    	
								    </c:forEach>
								    </select>
								</span>
						    </p>
						    <p class="control is-expanded">
						    	<c:if test="${requestScope.keyword==null}">
						      	<input name="keyword" class="input" type="text" placeholder="Keyword"/>
						    	</c:if>
						    	<c:if test="${requestScope.keyword!=null}">
						      	<input name="keyword" class="input is-info" type="text" placeholder="Keyword" value="${requestScope.keyword}"/>
						    	</c:if>
						    </p>
						    <p class="control">
							    <button class="button is-info">Search</button>
						  	</p>
						</div>
						<input type="hidden" name="action" value="${requestScope.action}"/>
					</form>
				</div>
			</div>
			<div class="level-right">
				<div class="level-item">
					<!-- get page context http://stackoverflow.com/questions/5850336/what-does-this-expression-language-pagecontext-request-contextpath-exactly-do -->
		    		<a href="${pageContext.request.contextPath}/admin/user?action=add" class="button is-primary">Add User</a>
				</div>
			</div>
		</div>
		
		<table class="table">
			<thead>
				<tr>
					<th>Username</th>
					<th></th>
					<th>Email</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${requestScope.paginations.totalRows == 0}">
				<tr>
					<td colspan="4" class="has-text-centered">
						Sorry, no data at the moment.
					</td>
				</tr>
			</c:if>
			<c:if test="${requestScope.paginations.totalRows > 0}">
			<c:forEach items="${requestScope.users}" var="user">
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/user?action=edit&id=${user.id}">${user.username}</a></td>
					<td>
						<figure class="image is-64x64">
							<c:choose>
								<c:when test="${not empty user.image}">
									<img src="${pageContext.request.contextPath}/files/${user.image}" alt="" />
								</c:when>
								<c:otherwise>
									<img src="http://placehold.it/64x64" alt="" />
								</c:otherwise>
							</c:choose>
						</figure>
					</td>
					<td>
						${user.email}
					</td>
					<td>
					<c:choose>
						<c:when test="${requestScope.action == 'list' || requestScope.action=='delete' }">
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
						<c:when test="${requestScope.action == 'trash' || requestScope.action=='restore' }">
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
			</c:if>
			</tbody>
		</table>
		<c:import url="../shared/_pagination.jsp">
		  <c:param name="itemsLength" value="${fn:length(requestScope.users)}"/>
		  <c:param name="routeTo" value="/admin/user" />
		  <c:param name="queryString" value="${requestScope.additionalQueryString}"/>
		</c:import>
	</jsp:body>
</t:layout-admin>