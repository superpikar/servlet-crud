package superpikar.servlet.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superpikar.servlet.admin.dao.PostDao;

/**
 * Servlet implementation class PostController
 */
public class PostClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE_LIST = "/views/client/post/post-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/client/post/post-single.jsp";
	private PostDao postDao;
	
	public PostClientController() {
		postDao = new PostDao();
	}
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = request.getParameter("id");
    	String page = "";
    	
    	if(id!=null){
			request.setAttribute("post", postDao.getPostById(Integer.valueOf(id)));			
			page = TEMPLATE_SINGLE;
		}
    	else {
    		request.setAttribute("posts", postDao.getAllPosts(false));			
			page = TEMPLATE_LIST;
    	}
    	RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
