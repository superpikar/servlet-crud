package superpikar.servlet.basic;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowSource
 */

public class ShowSource extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		// Get the servlet config
		ServletConfig config = getServletConfig();
		ServletContext sc = config.getServletContext();
		
		final String basePath = "/";
		
		// Check to see if a resource was requested
		String resource = request.getParameter("resource");		// GET request 
		if(resource != null && !resource.equals("")) {
			// Use getResourceAsStream() to properly get the file
			
			InputStream is = sc.getResourceAsStream(resource);
			if(is!=null){
				response.setContentType("text/plain");
				StringWriter sw = new StringWriter();
				for(int c=is.read(); c!=-1; c=is.read()){
					sw.write(c);
				}
				out.print(sw.toString());
			}
		}
		else {
			response.setContentType("text/html");
			out.println("<html>"
					+ 		"<head><title>Show Source</title></head>"
					+ 		"<body>"
					+ 			"<h3>Choose a resource to see the source </h3>"
					+ 			"<form>"
					+ 				"<select name='resource'>");
			
			listFiles(sc, out, basePath);
			out.println(			"</select>"
					+ 				"<input type='submit' value='Submit' />"
					+ 			"</form>"
					+ 		"</body>"
					+ "</html>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	protected void listFiles(ServletContext sc, PrintWriter out, String base) {
		Set set = sc.getResourcePaths(base);
		Iterator i = set.iterator();
		
		while(i.hasNext()) {
			String s = (String) i.next();
			if(s.endsWith("/")) {
				listFiles(sc, out, s);
			}
			else {
				out.println("<option value='"+s+"'>"+s+"</option>");						
			}
		}
	}
}
