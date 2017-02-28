<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-adminbasic>
	<jsp:attribute name="title">File Explorer</jsp:attribute>
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
		<c:import url="_file-list.jsp"></c:import>
	</jsp:body>
</t:layout-adminbasic>