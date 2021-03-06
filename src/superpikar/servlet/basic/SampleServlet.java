package superpikar.servlet.basic;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jtwig.web.servlet.JtwigRenderer;

public class SampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private final JtwigRenderer renderer = JtwigRenderer.defaultRenderer();
       
    /**
     * @throws IOException 
     * @see HttpServlet#HttpServlet()
     */
    public SampleServlet() throws IOException {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>"+
						"<head>"+
							"<title>Servlet CRUD</title>"+
						"</head>"+
						"<body>"+
							"<h1>Servlet CRUD</h1>"+
						"</body>"
				);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
