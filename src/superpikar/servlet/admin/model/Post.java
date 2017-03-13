package superpikar.servlet.admin.model;

import java.util.ArrayList;

/*
 * class of Post, the attributes/fields inspired by https://docs.bolt.cm/3.3/contenttypes/intro#an-example-news-items
 * */
public class Post extends BaseModel{
	private String slug;
	private String title;
	private String content;
	private String summary;
	private String image;
	private User user;
	private ArrayList<Term> terms;
	
	public Post(){
		this.setTerm(new ArrayList<Term>());
	}
	
	public Post(int id, String title, String slug, String content, String summary, String image){
		setProperties(id, title, slug, content, summary, image);
	}
	
	public void setProperties(int id, String title, String slug, String content, String summary, String image){
		this.setId(id);
		this.title = title;
		this.slug = slug;
		this.content = content;
		this.summary = summary;
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
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Term> getTerms() {
		return terms;
	}

	public void setTerm(ArrayList<Term> terms) {
		this.terms = terms;
	}
}
