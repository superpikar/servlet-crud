package superpikar.servlet.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superpikar.servlet.admin.dao.PostDao;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.util.PropUtil;

/**
 * Servlet implementation class PostController
 */
public class PostClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE_LIST = "/views/client/post/post-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/client/post/post-single.jsp";
	private PostDao postDao;
	private PropUtil propUtil;
	private FilterAndSort filterAndSort = new FilterAndSort();
	
	public PostClientController() {
		postDao = new PostDao();
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = request.getParameter("id");
		String template = "";
    	
    	propUtil = new PropUtil(getServletContext());
    	String pageNumber = request.getParameter("page");
    	String postPerPage = propUtil.getProperty("client.postperpage");
		
    	
    	if(id!=null){
			request.setAttribute("post", postDao.getPostById(Integer.valueOf(id)));			
			template = TEMPLATE_SINGLE;
		}
    	else {
    		request.setAttribute("posts", postDao.getAllPosts(false, pageNumber, postPerPage, filterAndSort));
    		template = TEMPLATE_LIST;
    	}
    	RequestDispatcher view = request.getRequestDispatcher(template);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
