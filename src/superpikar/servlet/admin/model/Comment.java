package superpikar.servlet.admin.model;

/*
 * class of Post, the attributes/fields inspired by https://docs.bolt.cm/3.3/contenttypes/intro#an-example-news-items
 * */
public class Comment extends BaseModel{
	private String comment;
	private int parentId;
	private String lineage;
	private String channel;
	private int channelId;
	private User user;
	
	public Comment(){
		setUser(new User());
	}
	
	public Comment(int id, String comment, int parentId, String lineage, String channel, int channelId){
		setProperties(id, comment, parentId, lineage, channel, channelId);
	}
	public void setProperties(int id, String comment, int parentId, String lineage, String channel, int channelId){
		this.setId(id);
		this.setComment(comment);
		this.setParentId(parentId);
		this.setLineage(lineage);
		this.setChannel(channel);
		this.setChannelId(channelId);
	}
	
	public int getLevel(){
		return this.lineage.split("/").length - 1;
	}
	
	public String getComment() {
		return comment;
	}	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
