<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:complexlayout>
	<jsp:attribute name="title">
		Page Title
	</jsp:attribute>
	<jsp:attribute name="header">
		<style>
			.title{
				background: #eaeaea;
			}
		</style>
	</jsp:attribute>
	
	<jsp:body>
		<h1 class="title">Hello</h1>
		<h2 class="subtitle">This template use WEB-INF/tags/complexlayout.tag as a base template :)</h2>
		
		<p>The current date is</p>
	</jsp:body>
</t:complexlayout>