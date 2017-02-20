package superpikar.servlet.admin.model;

import java.util.Date;

/**
 * 
 * @author http://twitter.com/superpikar
 *
	mandatory column 
	- registerIp                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	- registerUserId
	- registerDate
	- modificationIp
	- modificationDate
	- modificationUserId
	- deleted(yes/no)
 */
public class BaseModel {
	private int id;
	private String registerIp;
	private String modificationIp;
	private int registerUserId;
	private int modificationUserId;
	private Date registerDate;
	private Date modificationDate;
	private boolean deleted;
	
	public BaseModel() {
	}
	
	public void setRegisterProperties(String registerIp, int registerUserId, Date registerDate) {
		this.registerIp = registerIp;
		this.registerUserId = registerUserId;
		this.registerDate = registerDate;
	}
	
	public void setModificationProperties(String modificationIp, int modificationUserId, Date modificationDate) {
		this.modificationIp = modificationIp;
		this.modificationUserId = modificationUserId;
		this.modificationDate = modificationDate;
	}
	
	// http://stackoverflow.com/questions/7509697/java-check-to-see-if-a-variable-has-been-initialized
	public boolean isEmpty(){
		return getId()==0;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public int getRegisterUserId() {
		return registerUserId;
	}
	public void setRegisterUserId(int registerUserId) {
		this.registerUserId = registerUserId;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getModificationIp() {
		return modificationIp;
	}
	public void setModificationIp(String modificationIp) {
		this.modificationIp = modificationIp;
	}
	public int getModificationUserId() {
		return modificationUserId;
	}
	public void setModificationUserId(int modificationUserId) {
		this.modificationUserId = modificationUserId;
	}
	public Date getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
