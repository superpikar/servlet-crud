package superpikar.servlet.crud;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jtwig.web.servlet.JtwigRenderer;

/**
 * Servlet implementation class ServletCRUD
 */
@WebServlet("/home")
public class ServletCRUD extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private final JtwigRenderer renderer = JtwigRenderer.defaultRenderer();
       
    /**
     * @throws IOException 
     * @see HttpServlet#HttpServlet()
     */
    public ServletCRUD() throws IOException {
        super();
        // TODO Auto-generated constructor stub
        /* Create and adjust the configuration singleton */
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
//		request.setAttribute("variable", "Hello");
//		renderer.dispatcherFor("/WEB-INF/templates/index.twig.html")
//	        .with("name", "Jtwig")
//	        .render(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
