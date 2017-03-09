<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-adminbasic>
	<jsp:attribute name="title">
		Login
	</jsp:attribute>
	
	<jsp:attribute name="header">
	</jsp:attribute>
	
	<jsp:attribute name="footer">
		<script>
			var app = new Vue({
			  	el: '#app',
			  	data: {
					
			  	},
			  	mounted: function() {
				  	 
			  	},
			  	methods: {
			  	}
			});			
		</script>
	</jsp:attribute>
	
	<jsp:body>
		<!-- login template taken from https://github.com/dansup/bulma-templates -->
		<section class="hero is-fullheight is-dark is-bold">
			<div class="hero-body">
				<div class="container">
					<div class="columns is-vcentered">
						<div class="column is-4 is-offset-4">
							<h1 class="title has-text-centered">Login</h1>
							<div class="box">
								<c:if test="${not empty sessionScope.message}">
								<article class="message is-danger">
						  			<div class="message-body">
										${sessionScope.message}
									</div>
								</article>
								</c:if>
								<form action="${pageContext.request.contextPath}/admin/login" method="post">
					              	<label class="label">Username</label>
					              	<p class="control">
					                	<input class="input" type="text" name="username" placeholder="username">
					              	</p>
					              	<label class="label">Password</label>
					              	<p class="control">
					                	<input class="input" type="password" name="password" placeholder="●●●●●●●">
					              	</p>
					              	<p class="control" style="margin-top:20px;">
					              		<input type="hidden" name="from" value="${requestScope.from}"/>
					                	<button class="button is-primary is-fullwidth">Login</button>
					              	</p>
								</form>
							</div>
							<p class="has-text-centered">
								Pikar CMS @2017
							</p>
						</div>
					</div>
				</div>
			</div>
		</section>
	</jsp:body>
</t:layout-adminbasic>