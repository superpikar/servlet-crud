package superpikar.servlet.util;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

public class PropUtil {
	private Properties properties;
	private final String PROPFILE = "/resources/servlet-crud.properties";
	
	public PropUtil(ServletContext servletContext) throws IOException{
		properties = new Properties();
		properties.load(servletContext.getResourceAsStream(PROPFILE));
	}
	
	public PropUtil(ServletContext servletContext, String propFile) throws IOException{
		properties = new Properties();
		properties.load(servletContext.getResourceAsStream(propFile));
	}
	
	public String getProperty(String key){
		return properties.getProperty(key);
	}
	
	public String[] getPropertyAsArray(String key){
		String[] a = properties.getProperty(key).split(",");
		
		return properties.getProperty(key).split(",");
	}
	
	public Properties getProperties(){
		return properties;
	}
}
