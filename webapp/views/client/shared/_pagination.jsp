<%-- param: {
		itemsLength: length of the items, 
		routeTo: route to URL,
		queryString: if any request string 	
 	}
 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<c:if test="${requestScope.paginations.totalRows > 0}">
Showing ${requestScope.paginations.firstRowNumber}~${requestScope.paginations.lastRowNumber} of ${requestScope.paginations.totalRows}
<nav class="pagination">
	<ul class="pagination-list">
		<c:forEach items="${requestScope.paginations.paginations}" var="pagination">
	    <li>
	    	<c:if test="${requestScope.paginations.pageNumber==pagination}">
	    	<a class="pagination-link is-current" href="${pageContext.request.contextPath}${param.routeTo}?page=${pagination}${param.queryString}">${pagination}</a>
	    	</c:if>
	    	<c:if test="${requestScope.paginations.pageNumber!=pagination}">
	    	<a class="pagination-link" href="${pageContext.request.contextPath}${param.routeTo}?page=${pagination}${param.queryString}">${pagination}</a>
	    	</c:if>
	  	</li>
		</c:forEach>
	</ul>
</nav>
</c:if>