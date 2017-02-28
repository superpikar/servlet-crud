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
		<jsp:attribute name="footer">
		<script type="text/javascript" src="${pageContext.request.contextPath}/libs/naversmarteditor/js/service/HuskyEZCreator.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/NaverSmartEditor.js"></script>
		<script>
		
			function clickModel(){
				console.log('click model');
				$('.modal iframe').contents().find('.card').click(function(){
					alert('hello from iframe');
				});
			}
			
			var app = new Vue({
			  	el: '#app',
			  	data: {
					title: "${requestScope.post.title}",
			    	slug: "${requestScope.post.slug}",
			    	showModal: false
			  	},
			  	mounted: function() {
			 	  	this.editor = new NaverSmartEditor("editor", "${pageContext.request.contextPath}/libs/naversmarteditor/SmartEditor2Skin.html");
			 	  	
			 	  	window.setTimeout(function(){
			 	  		clickModel();
			 	  	}, 1000);
			  	},
			  	methods: {
				  	updateSlug: function() {
					  	this.slug = _.kebabCase(this.title)
				  	},
				  	submitForm: function(e) {
					  	//e.preventDefault();
					 	var content = this.editor.setEditorValue();
					  	//console.log(content);
				  	},
				  	showFileExplorer: function(isShow){
				  		this.showModal = isShow;
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
						<input type="file" name="thumbnail" value="${requestScope.post.image}"/>
						<a href="#" class="button is-primary" v-on:click="showFileExplorer(true)">Select image</a>
					</div>
					<div class="control">
						<p>Preview : ${requestScope.post.image}</p>
						<figure class="image is-128x128">
							<img src="${pageContext.request.contextPath}/images/${requestScope.post.image}" alt="" />
						</figure>
					</div>
				</div>
				<label class="label">Content</label>
				<p class="control">
					<textarea name="content" id="editor">
						${requestScope.post.content} 
					</textarea>						
				</p>
				<input type="hidden" name="id" value="${requestScope.post.id}" />
				<input type="hidden" name="slug" v-bind:value="slug"/>
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="currentThumbnail" value="${requestScope.post.image}"/>
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
				    		<span class="tag">Created : ${requestScope.post.registerDate}</span><br/>
				    		<span class="tag">Updated : ${requestScope.post.modificationDate}</span>
				    		</c:if>
				      	</div>
				    </div>
				</div>
			</div>
		</div>
	</form>
	<c:import url="../file/_modal-file-explorer.jsp"></c:import>
	</jsp:body>
</t:layout-admin>