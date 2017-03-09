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

public class HomeClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE = "/views/client/home/index.jsp";
	private PostDao postDao;
	private PropUtil propUtil;
	private FilterAndSort filterAndSort = new FilterAndSort();
       
    public HomeClientController() {
    	postDao = new PostDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		propUtil = new PropUtil(getServletContext());
    	String postPerPage = propUtil.getProperty("frontend.news.itemsperpage");
    	String pageNumber = request.getParameter("page");
    	
    	request.setAttribute("paginations", postDao.getPaginationResult(false, pageNumber, postPerPage, filterAndSort));
		request.setAttribute("posts", postDao.getAllPosts(false, pageNumber, postPerPage, filterAndSort));
		RequestDispatcher view = request.getRequestDispatcher(TEMPLATE);
		view.forward(request, response);
	}
}
