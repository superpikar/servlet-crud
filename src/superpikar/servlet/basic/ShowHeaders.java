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
 * Servlet implementation class ShowHeaders
 */
@WebServlet("/ShowHeaders")
public class ShowHeaders extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+ 		"<head>"
				+ 			"<title>Request's HTTP Headers</title>"
				+ 		"</head>"
				+ 		"<body>"
				+ 			"<p>HTTP headers sent by your client </p>");
		Enumeration e = request.getHeaderNames();
		while(e.hasMoreElements()) {
			String headerName = (String) e.nextElement();
			String headerValue = request.getHeader(headerName);
			out.println("<b>"+headerName+"</b> : "+ headerValue+"<br/>");
		}
		out.println("</body>"
				+ "</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
