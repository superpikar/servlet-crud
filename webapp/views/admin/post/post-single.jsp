<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout-admin>
	<jsp:attribute name="title">
		<c:choose>
			<c:when test="${action == 'add'}">
				<c:set var="title" value="New Post"/>
				<c:set var="buttonTitle" value="Save Post"/>
				<c:set var="action" value="add"/>
			</c:when>
			<c:otherwise>
				<c:set var="title" value="Edit Post"/>
				<c:set var="buttonTitle" value="Update Post"/>
				<c:set var="action" value="edit"/>
			</c:otherwise>
		</c:choose>
		<c:out value="${title}" />
	</jsp:attribute>
	
	<jsp:attribute name="header">
	</jsp:attribute>
	
	<jsp:attribute name="footer">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.4/lodash.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/libs/naversmarteditor/js/service/HuskyEZCreator.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/NaverSmartEditor.js"></script>
		<script>
			var app = new Vue({
			  	el: '#app',
			  	data: {
					title: "${post.title}",
			    	slug: "${post.slug}"
			  	},
			  	mounted: function() {
				  	this.editor = new NaverSmartEditor("editor", "${pageContext.request.contextPath}/libs/naversmarteditor/SmartEditor2Skin.html"); 
			  	},
			  	methods: {
				  	updateSlug: function() {
					  	this.slug = _.kebabCase(this.title)
				  	},
				  	submitForm: function(e) {
					  	//e.preventDefault();
					 	var content = this.editor.setEditorValue();
					  	//console.log(content);
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
		<form action="${pageContext.request.contextPath}/admin/news" method="post" enctype="multipart/form-data">
		<div class="columns">
			<div class="column is-9">
				<label class="label">Title</label>
				<p class="control">
				  	<input class="input" name="title" type="text" placeholder="Title" v-model="title" v-on:keyup="updateSlug"/>
				</p>
				<label class="label">Slug</label>
				<p class="control">
					<span>{{slug}}</span>
				</p>
				<label class="label">Thumbnail</label>
				<div class="control is-grouped">
					<div class="control is-expanded">
						<input type="file" name="thumbnail" value="${post.image}"/>
					</div>
					<div class="control">
						<p>Preview : ${post.image}</p>
						<figure class="image is-128x128">
							<img src="${pageContext.request.contextPath}/images/${post.image}" alt="" />
						</figure>
					</div>
				</div>
				<label class="label">Content</label>
				<p class="control">
					<textarea name="content" id="editor">
						${post.content} 
					</textarea>						
				</p>
				<input type="hidden" name="id" value="${post.id}" />
				<input type="hidden" name="slug" v-bind:value="slug"/>
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="currentThumbnail" value="${post.image}"/>
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
				    		<span class="tag">Created : ${post.registerDate}</span><br/>
				    		<span class="tag">Updated : ${post.modificationDate}</span>
				    		</c:if>
				      	</div>
				    </div>
				</div>
			</div>
		</div>
	</form>
	</jsp:body>
</t:layout-admin>