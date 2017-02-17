<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">
		Dashboard
	</jsp:attribute>
	
	<jsp:attribute name="header">
	</jsp:attribute>
	
	<jsp:attribute name="footer">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.4/lodash.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>
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
		<h1 class="title">
			Dashboard
		</h1>
		<c:if test="${not empty sessionScope.message}">
		<article class="message is-success">
  			<div class="message-body">
				${sessionScope.message}
			</div>
		</article>
		</c:if>
	</jsp:body>
</t:layout-admin>