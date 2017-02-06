package superpikar.servlet.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowParameters
 */
@WebServlet("/ShowParameters")
public class ShowParameters extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+ 		"<head>"
				+ 			"<title>Request HTTP Parameter Sent</title>"
				+ 		"</head>"
				+ 		"<body>"
				+ 			"<p>Parameter with request </p>");
		Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()) {
			String paramName = (String) e.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			out.println("<b>"+paramName+"</b> : ");
			for(int i=0; i<paramValues.length; i++) {
				out.println(paramValues[i]);
			}
			out.println("<br/>");
		}
		out.println("</body>"
				+ "</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
