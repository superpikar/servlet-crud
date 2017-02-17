package superpikar.servlet.model;

/**
 * Class User, representing user in the system
 *
	CREATE TABLE `pikarcms_user` (
	  `id` int(11) NOT NULL,
	  `username` varchar(32) DEFAULT NULL,
	  `password` varchar(32) DEFAULT NULL,
	  `email` varchar(128) DEFAULT NULL,
	  `website` varchar(128) DEFAULT NULL,
	  `image` varchar(256) DEFAULT NULL,
	  `registerIp` varchar(19) DEFAULT NULL,
	  `modificationIp` varchar(19) DEFAULT NULL,
	  `modificationUserId` int(11) DEFAULT NULL,
	  `registerUserId` int(11) NOT NULL,
	  `registerDate` datetime DEFAULT NULL,
	  `modificationDate` datetime DEFAULT NULL,
	  `deleted` int(11) DEFAULT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	
	ALTER TABLE `pikarcms_user`
	  ADD PRIMARY KEY (`id`);
	
	ALTER TABLE `pikarcms_user`
	  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
	  
 * @author http://twitter.com/superpikar
 */
public class User extends BaseModel {
	private String username;
	private String password;
	private String email;
	private String website;
	private String image;
	
	public User(){
		
	}
	
	public User(int id, String username, String password, String email, String website, String image){
		setProperties(id, username, password, email, website, image);
	}
	
	public void setProperties(int id, String username, String password, String email, String website, String image){
		this.setId(id);
		this.username = username;
		this.password = password;
		this.email = email;
		this.website = website;
		this.image = image;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}	
}
