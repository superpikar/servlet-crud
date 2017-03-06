package superpikar.servlet.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superpikar.servlet.admin.dao.PostDao;
import superpikar.servlet.util.PropUtil;

public class HomeClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE = "/views/client/home/index.jsp";
	private PostDao postDao;
	private PropUtil propUtil;
	private int postPerPage;
       
    public HomeClientController() {
    	postDao = new PostDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		int pageNumber = page==null?1:Integer.parseInt(page);
		
		propUtil = new PropUtil(getServletContext());
    	postPerPage = Integer.valueOf(propUtil.getProperty("frontend.news.itemsperpage"));
    	request.setAttribute("paginations", postDao.getPaginationResult(false, postPerPage));
		request.setAttribute("posts", postDao.getAllPosts(false, pageNumber, postPerPage));
		request.setAttribute("pageNumber", pageNumber);
		RequestDispatcher view = request.getRequestDispatcher(TEMPLATE);
		view.forward(request, response);
	}
}
