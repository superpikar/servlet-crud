<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="firstData" value="${((requestScope.pageNumber-1)*requestScope.paginations.postPerPage) + 1}"></c:set>
<c:set var="lastData" value="${firstData-1+param.itemsLength}"></c:set>
Showing ${firstData}~${lastData} of ${requestScope.paginations.totalRows}
<nav class="pagination">
	<ul class="pagination-list">
		<c:forEach items="${requestScope.paginations.paginations}" var="pagination">
	    <li>
	    	<c:if test="${requestScope.pageNumber==pagination}">
	    	<a class="pagination-link is-current" href="${pageContext.request.contextPath}${param.routeTo}/?page=${pagination}${param.queryString}">${pagination}</a>
	    	</c:if>
	    	<c:if test="${requestScope.pageNumber!=pagination}">
	    	<a class="pagination-link" href="${pageContext.request.contextPath}${param.routeTo}/?page=${pagination}${param.queryString}">${pagination}</a>
	    	</c:if>
	  	</li>
		</c:forEach>
	</ul>
</nav>