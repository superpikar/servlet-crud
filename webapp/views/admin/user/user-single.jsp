<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">
		<c:choose>
			<c:when test="${action == 'add'}">
				<c:set var="title" value="New User"/>
				<c:set var="buttonTitle" value="Save User"/>
				<c:set var="action" value="add"/>
			</c:when>
			<c:otherwise>
				<c:set var="title" value="Edit User"/>
				<c:set var="buttonTitle" value="Update User"/>
				<c:set var="action" value="edit"/>
			</c:otherwise>
		</c:choose>
		<c:out value="${title}" />
	</jsp:attribute>
	
	<jsp:attribute name="header">
	</jsp:attribute>
	
	<jsp:attribute name="footer">
		<script>
			var app = new Vue({
			  	el: '#app',
			  	data: {
			  		password: "${requestScope.user.password}",
			  		confirmPassword: "${requestScope.user.password}"
			  	},
			  	methods: {
			  		isPasswordEqual: function() {
			  			if(this.confirmPassword==""){
			  				return false;
			  			}
			  			else if(this.confirmPassword==this.password){
			  				return true;
			  			}
			  			else {
			  				return false;
			  			}
					},
				  	submitForm: function(e) {
					}
			  	}
			});			
		</script>
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title">
			<c:out value="${title}" />
		</h1>
		
		<c:if test="${not empty sessionScope.message}">
		<article class="message is-success">
  			<div class="message-body">
				${sessionScope.message}
			</div>
		</article>
		</c:if>
		<form action="${pageContext.request.contextPath}/admin/user" method="post" enctype="multipart/form-data">
		<div class="columns">
			<div class="column is-9">
				<label class="label">Username</label>
				<p class="control">
					<c:if test="${action=='add'}">
				  	<input class="input" name="username" type="text" placeholder="username"/>
			      	</c:if>
			      	<c:if test="${action=='edit'}">
			      	<!-- SET NAME ATTRIBUTE WITH DISABLED, WILL RESULT NULL VALUE, THEN IT'S BETTER TO REMOVE NAME ATTRIBUTE -->
			      	<input class="input" type="text" disabled value="${requestScope.user.username}"/>
			      	<input type="hidden" name="username" value="${requestScope.user.username}" />
			      	<span class="help">Sorry, username is unchangeable.</span>
			      	</c:if>
				</p>
				<label class="label">Roles</label>
				<p class="control">
				  	<span class="select">
					    <select name="role">
					    	<c:forEach items="${requestScope.userRoles}" var="role">
					      	<c:if test="${requestScope.user.role!=role}">
					      	<option value="${role}">${role}</option>
					      	</c:if>
					      	<c:if test="${requestScope.user.role==role}">
					      	<option value="${role}" selected>${role}</option>
					      	</c:if>
					    	</c:forEach>
					    </select>
					</span>
					<span class="help">Please logout to see the result change.</span>
				</p>
				<label class="label">Password</label>
				<p class="control">
				  	<input class="input" name="password" type="password" v-model="password"/>
				</p>
				<label class="label">Confirm password</label>
				<p class="control has-icon has-icon-right">
				  	<input class="input" name="confirmpassword" type="password" v-model="confirmPassword" v-bind:class="{'is-danger': !isPasswordEqual(), 'is-success': isPasswordEqual()}"/>
				  	<span class="icon is-small" v-if="!isPasswordEqual()">
					    <i class="fa fa-warning"></i>
				  	</span>
				  	<span class="help is-danger" v-if="!isPasswordEqual()">Password not equal</span>
				  	
				  	<span class="icon is-small" v-if="isPasswordEqual()">
					    <i class="fa fa-check"></i>
					</span>
					<span class="help is-success" v-if="isPasswordEqual()">Good! password is equal</span>
				</p>
				<label class="label">Email</label>
				<p class="control">
				  	<input class="input" name="email" type="text" placeholder="username@mail.com" value="${requestScope.user.email}"/>
				</p>
				<label class="label">Website</label>
				<p class="control">
				  	<input class="input" name="website" type="text" placeholder="http://yourwebsite.com" value="${requestScope.user.website}"/>
				</p>
				<label class="label">Thumbnail</label>
				<div class="control is-grouped">
					<div class="control is-expanded">
						<input type="file" name="thumbnail" value="${requestScope.user.image}"/>
					</div>
					<div class="control">
						<p>Preview : ${user.image}</p>
						<figure class="image is-128x128">
							<img src="${pageContext.request.contextPath}/images/${requestScope.user.image}" alt="" />
						</figure>
					</div>
				</div>
				<input type="hidden" name="id" value="${requestScope.user.id}" />
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="currentThumbnail" value="${requestScope.user.image}"/>
			</div>
			<div class="column is-3">
				<div class="card">
				  	<div class="card-content">
				    	<div class="content">
				    		<button class="button is-primary is-fullwidth" v-on:click="submitForm">
				    			<span class="icon"><i class="fa fa-check"></i></span> 
				    			<span>${buttonTitle}</span>
				    		</button>
				    		<c:if test="${action == 'edit'}">
				    		<hr />
				    		<span class="tag">Created : ${requestScope.user.registerDate}</span><br/>
				    		<span class="tag">Updated : ${requestScope.user.modificationDate}</span>
				    		</c:if>
				      	</div>
				    </div>
				</div>
			</div>
		</div>
	</form>
	</jsp:body>
</t:layout-admin>