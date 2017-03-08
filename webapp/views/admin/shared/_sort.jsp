<%-- param: {
		formActionTo: form action URI	 	
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
						    <select name="sortColumn">
						    <c:forEach items="${requestScope.filterAndSort.sortColumns}" var="val">
						    	<c:if test="${val.key==requestScope.filterAndSort.sortColumn}">
						      	<option selected value="${val.key}">${val.value}</option>
						    	</c:if>
						    	<c:if test="${val.key!=requestScope.filterAndSort.sortColumn}">
						      	<option value="${val.key}">${val.value}</option>
						    	</c:if>							    	
						    </c:forEach>
						    </select>
						</span>
				    </p>
				    <p class="control is-expanded">
				    	<c:if test="${requestScope.filterAndSort.sortOrder!=null}">
				    		<c:set var="sortOrderClass" value="is-info"></c:set>
				    	</c:if>
				    	<span class="select ${sortOrderClass}">
						    <select name="sortOrder">
						    	<c:if test="${requestScope.filterAndSort.sortOrder=='ASC'}">
						      	<option selected value="ASC">Ascending</option>
						    	</c:if>
						    	<c:if test="${requestScope.filterAndSort.sortOrder!='ASC'}">
						      	<option value="ASC">Ascending</option>
						    	</c:if>
						    	
						    	<c:if test="${requestScope.filterAndSort.sortOrder=='DESC'}">
						      	<option selected value="DESC">Descending</option>
						    	</c:if>
						    	<c:if test="${requestScope.filterAndSort.sortOrder!='DESC'}">
						      	<option value="DESC">Descending</option>
						    	</c:if>
						    </select>
						</span>
				    </p>						    
				    <p class="control">
					    <button class="button is-info">Sort</button>
				  	</p>
				</div>
				<input type="hidden" name="action" value="${requestScope.action}"/>
				<input type="hidden" name="condition" value="${requestScope.filterAndSort.condition}"/>
				<input type="hidden" name="keyword" value="${requestScope.filterAndSort.keyword}"/>
			</form>
		</div>
	</div>
	<div class="level-right">
		<div class="level-item">
			
		</div>
	</div>
</div>