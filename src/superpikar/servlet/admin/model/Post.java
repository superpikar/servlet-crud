package superpikar.servlet.admin.model;

/*
 * class of Post, the attributes/fields inspired by https://docs.bolt.cm/3.3/contenttypes/intro#an-example-news-items
 * */
public class Post extends BaseModel{
	private String slug;
	private String title;
	private String content;
	private String image;
	
	public Post(){
		
	}
	
	public Post(int id, String title, String slug, String content, String image){
		setProperties(id, title, slug, content, image);
	}
	
	public void setProperties(int id, String title, String slug, String content, String image){
		this.setId(id);
		this.title = title;
		this.slug = slug;
		this.content = content;
		this.image = image;
	}
	
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
