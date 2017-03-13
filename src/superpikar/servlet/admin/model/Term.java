package superpikar.servlet.admin.model;

/*
 * class of Post, the attributes/fields inspired by https://docs.bolt.cm/3.3/contenttypes/intro#an-example-news-items
 * */
public class Term extends BaseModel{
	private String name;
	private String slug;
	private String taxonomy;
	private String lineage;
	
	public Term(){
		
	}
	
	public Term(int id, String name, String slug, String taxonomy, String lineage){
		setProperties(id, name, slug, taxonomy, lineage);
	}
	
	public void setProperties(int id, String name, String slug, String taxonomy, String lineage){
		this.setId(id);
		this.name = name;
		this.slug = slug;
		this.taxonomy = taxonomy;
		this.lineage = lineage;
	}
	
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}
}
