package superpikar.servlet.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superpikar.servlet.admin.dao.PostDao;

public class HomeClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE = "/views/client/home/index.jsp";
	private PostDao postDao;
       
    public HomeClientController() {
    	postDao = new PostDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("posts", postDao.getAllPosts(false));
		RequestDispatcher view = request.getRequestDispatcher(TEMPLATE);
		view.forward(request, response);
	}
}
