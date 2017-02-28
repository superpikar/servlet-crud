<div class="modal file-explorer-container" v-bind:class="{'is-active': showModal}">
  <div class="modal-background" v-on:click="showFileExplorer(false)"></div>
  <div class="modal-content">
    <iframe src="${pageContext.request.contextPath}/admin/files?layout=basic" frameborder="0"></iframe>
  </div>
  <button class="modal-close" v-on:click="showFileExplorer(false)"></button>
</div>