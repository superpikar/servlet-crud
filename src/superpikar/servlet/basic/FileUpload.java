package superpikar.servlet.basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sun.istack.internal.logging.Logger;

/**
 * Servlet implementation class FileUpload
 * credit : https://docs.oracle.com/javaee/6/tutorial/doc/glraq.html
 */
@WebServlet("/FileUpload")
@MultipartConfig
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		// create path components to save the file
		final String path = "uploadfiles";
		final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);
		
		OutputStream outputstream = null;
		InputStream filecontent = null;
		PrintWriter out = response.getWriter();
		
		String root = getServletContext().getRealPath("/");
		String fileLocation = path + File.separator + fileName ;
		
		try {
			File f = new File(root+fileLocation);
			
			// check if file not exist http://stackoverflow.com/questions/9620683/java-fileoutputstream-create-file-if-not-exists
			// remember that file is created in the server, not in the project directory.
			if(!f.exists()){
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			
			outputstream= new FileOutputStream(f);
			filecontent = filePart.getInputStream();
			
			int read = 0;
			final byte[] bytes = new byte[1024];
			
			while((read = filecontent.read(bytes)) != -1) {
				outputstream.write(bytes, 0, read);
			}
			
			out.println("<html>"
					+ 		"<head>"
					+ 			"<title>Request's HTTP Headers</title>"
					+ 		"</head>"
					+ 		"<body>"
					+ 			"<p>Actual location : " + root+fileLocation + "</p>");
			out.println("<p>New file " + fileName + " created at " + path + "</p>");
			out.println("<p>Click <a class='button' href='/servlet-crud/uploadfiles'>here</a> to browse through all uploaded files</p>");
			out.println("</body></html>");
		} catch (FileNotFoundException fne) {
			out.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
			out.println("<br/> ERROR: " + fne.getMessage());
			out.println("<br/> ERROR: " + fne.getStackTrace());
	        fne.printStackTrace();
	    } finally {
			if (outputstream != null) {
				outputstream.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (out != null) {
	        	out.close();
	        }
		}
	}
	
	protected String getFileName(final Part filePart) {
		final String partHeader = filePart.getHeader("content-disposition");
		
		for(String content : filePart.getHeader("content-disposition").split(";")){
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=')+1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
