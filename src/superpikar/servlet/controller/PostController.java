package superpikar.servlet.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import superpikar.servlet.dao.PostDao;
import superpikar.servlet.model.Post;

/**
 * Servlet implementation class PostController
 */
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostDao postDao;
	private final String TEMPLATE_LIST = "/views/admin/post/post-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/admin/post/post-single.jsp";
	
	public PostController(){
		postDao = new PostDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String page = "";
		
		if(action==null){
			session.removeAttribute("message");	// clear session
			request.setAttribute("posts", postDao.getAllPosts());			
			page = TEMPLATE_LIST;
		}
		else {
			if(action.equalsIgnoreCase("add")) {
				page = TEMPLATE_SINGLE;
			}
			else if(action.equalsIgnoreCase("edit")) {
				int ID= Integer.parseInt(request.getParameter("id"));
				request.setAttribute("post", postDao.getPostById(ID));
				page = TEMPLATE_SINGLE;
			}
			else if(action.equalsIgnoreCase("delete")) {
				int ID= Integer.parseInt(request.getParameter("id"));
				postDao.deletePost(ID);
				request.setAttribute("posts", postDao.getAllPosts());	
				page = TEMPLATE_LIST;
			}
		}
		
		request.setAttribute("action", action);
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		int resultId;
		
		Post post = new Post();
		post.setTitle(request.getParameter("title"));
		post.setSlug(request.getParameter("slug"));
		post.setContent(request.getParameter("content"));
		post.setUserID(1);
		
		// IF ID IS NULL THEN CREATE USER, OTHERWISE UPDATE USER
		if(id == null || id.isEmpty()) {
			resultId = postDao.addPost(post);
			session.setAttribute("message", "Create new post success!");
		} 
		else {
			post.setID(Integer.parseInt(id));
			resultId = postDao.updatePost(post);
			session.setAttribute("message", "Update '"+post.getTitle()+"' success!");
		}
		response.sendRedirect(request.getContextPath()+"/admin/news?action=edit&id="+resultId);
	}

}
