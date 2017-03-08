<%-- param: {
		formActionTo: form action URI	,
		addRouteTo: route to of add data
		addButtonText  	
 	}
 --%>
 
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<div class="level">
	<div class="level-left">
		<div class="level-item">
			<form action="${pageContext.request.contextPath}${param.formActionTo}">
				<div class="control is-grouped">
				    <p class="control is-expanded">
				      	<span class="select">
						    <select name="condition">
						    <c:forEach items="${requestScope.filterAndSort.searchConditions}" var="val">
						    	<c:if test="${val.key==requestScope.filterAndSort.condition}">
						      	<option selected value="${val.key}">${val.value}</option>
						    	</c:if>
						    	<c:if test="${val.key!=requestScope.filterAndSort.condition}">
						      	<option value="${val.key}">${val.value}</option>
						    	</c:if>							    	
						    </c:forEach>
						    </select>
						</span>
				    </p>
				    <p class="control is-expanded">
				    	<c:if test="${requestScope.filterAndSort.keyword!=null}">
				    		<c:set var="keywordClass" value="is-info"></c:set>
				    	</c:if>
				    	<input name="keyword" class="input ${keywordClass}" type="text" placeholder="Keyword" value="${requestScope.filterAndSort.keyword}"/>
				    </p>
				    <p class="control">
					    <button class="button is-info">Search</button>
				  	</p>
				</div>
				<input type="hidden" name="action" value="${requestScope.action}"/>
				<input type="hidden" name="sortColumn" value="${requestScope.filterAndSort.sortColumn}"/>
				<input type="hidden" name="sortOrder" value="${requestScope.filterAndSort.sortOrder}"/>
			</form>
		</div>
	</div>
	<div class="level-right">
		<div class="level-item">
			<!-- get page context http://stackoverflow.com/questions/5850336/what-does-this-expression-language-pagecontext-request-contextpath-exactly-do -->
    		<a href="${pageContext.request.contextPath}${param.addRouteTo}" class="button is-primary">${param.addButtonText}</a>
		</div>
	</div>
</div>