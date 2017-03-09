<div id="comment-${param.id}" class="blog-comment media">
  <figure class="media-left">
    <p class="image is-64x64">
      <img src="http://bulma.io/images/placeholders/128x128.png">
    </p>
  </figure>
  <div class="media-content">
    <div class="content">
      <p>
        <strong>${param.username}</strong> <small>${param.email}</small> <small>${param.registerDate}</small>
        <br>
        <p>${param.comment}</p>
      </p>
    </div>
    <nav class="level">
      <div class="level-left">
        <a class="level-item">
          <span class="icon is-small"><i class="fa fa-reply"></i></span>
        </a>
        <a class="level-item">
          <span class="icon is-small"><i class="fa fa-retweet"></i></span>
        </a>
        <a class="level-item">
          <span class="icon is-small"><i class="fa fa-heart"></i></span>
        </a>
      </div>
    </nav>
  </div>
</div>