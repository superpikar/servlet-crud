package superpikar.servlet.basic;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.ws.api.pipe.NextAction;

/**
 * Servlet implementation class LinkTracker, from http://java.cnam.fr/iagl/biblio/Serlvets%20&%20JSP%20-%20Falkner%20Jones.pdf page 60
 */
@WebServlet("/LinkTracker")
public class LinkTracker extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Hashtable links = new Hashtable();
    String tstamp; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinkTracker() {
        super();
        tstamp = new Date().toString();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String link = request.getParameter("link");
		
		if(link != null && !link.equals("")) {
			synchronized (links) {
				Integer count = (Integer) links.get(link);
				if(count == null) {
					links.put(link, new Integer(1));
				}
				else {
					links.put(link, 1 + count.intValue());
				}
			}
//			response.setHeader("Refresh", "10; URL="+link);
			response.sendRedirect(link);	// redirect to the link
		}
		else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			request.getSession();
			
			out.println("<html>"
					+ 		"<head>"
					+ 			"<title>Link Tracer</title>"
					+ 		"</head>"
					+ 		"<body>"
					+ 			"<p>Link Tracked Since : "
					+ 			tstamp + "</p>");
			
			if(links.size() !=0) {
				Enumeration e = links.keys();
				while(e.hasMoreElements()) {
					String key = (String)e.nextElement();
					int count = ((Integer)links.get(key)).intValue();
					out.println(key+": "+count+" visited <br/>");
				}
			}
			else {
				out.println("No links have been tracked! ! <br/>");
			}
			
			out.println("</body>"
					+ "</html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
