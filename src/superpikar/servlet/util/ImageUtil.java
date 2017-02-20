package superpikar.servlet.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class ImageUtil {
	public static String getFileName(Part filePart) {
		final String partHeader = filePart.getHeader("content-disposition");
		
		for(String content : filePart.getHeader("content-disposition").split(";")){
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=')+1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
	public static String saveFileToDirectory(Part filePart, ServletContext servletContext) throws IOException{
		Properties properties = new Properties();
		properties.load(servletContext.getResourceAsStream("/resources/servlet-crud.properties"));
		
		// SAVE FILE TO DIRECTORY
		// credit http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
		String fileName = getFileName(filePart);
	    File uploads = new File(properties.getProperty("UPLOAD_DIR"));
	    File file = new File(uploads, fileName);
	    System.out.println("filename "+properties.getProperty("UPLOAD_DIR")+"/"+fileName);
	    try (InputStream filecontent = filePart.getInputStream()) {
	        Files.copy(filecontent, file.toPath());
	    }
		return fileName;
	}
}
