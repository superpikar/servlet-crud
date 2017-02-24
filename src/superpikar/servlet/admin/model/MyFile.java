package superpikar.servlet.admin.model;

public class MyFile {
	private String name;
	private String path;
	private boolean directory;
	
	public MyFile(String name, String path, boolean directory){
		this.name = name;
		this.path = path;
		this.directory = directory;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isDirectory() {
		return directory;
	}
	public void setDirectory(boolean isDirectory) {
		this.directory = isDirectory;
	}
}
