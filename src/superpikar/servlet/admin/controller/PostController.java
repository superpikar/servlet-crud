package superpikar.servlet.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import superpikar.servlet.admin.dao.PostDao;
import superpikar.servlet.admin.model.Post;
import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.ImageUtil;
import superpikar.servlet.util.PropUtil;

/**
 * Servlet implementation class PostController
 */
@MultipartConfig
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostDao postDao;
	private PropUtil propUtil;
	private int postPerPage;
	private final String TEMPLATE_LIST = "/views/admin/post/post-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/admin/post/post-single.jsp";
	
	public PostController(){
		postDao = new PostDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		propUtil = new PropUtil(getServletContext());
		postPerPage = Integer.valueOf(propUtil.getProperty("backend.news.itemsperpage"));
		String action = request.getParameter("action");
		String page = request.getParameter("page");
		int pageNumber = page==null?1:Integer.parseInt(page);
		String template = "";		
		
		if(action==null){
			session.removeAttribute("message");	// clear session				
			request.setAttribute("posts", postDao.getAllPosts(false, pageNumber, postPerPage));
			request.setAttribute("paginations", postDao.getPaginationResult(false, postPerPage));
			template = TEMPLATE_LIST;
		}
		else {
			if(action.equalsIgnoreCase("trash")) {
				request.setAttribute("posts", postDao.getAllPosts(true, pageNumber, postPerPage));
				request.setAttribute("paginations", postDao.getPaginationResult(true, postPerPage));
				template = TEMPLATE_LIST;
			}
			else if(action.equalsIgnoreCase("add")) {
				template = TEMPLATE_SINGLE;
			}
			else if(action.equalsIgnoreCase("edit")) {
				int id= Integer.parseInt(request.getParameter("id"));
				request.setAttribute("post", postDao.getPostById(id));
				template = TEMPLATE_SINGLE;
			}
			else if(action.equalsIgnoreCase("delete")) {
				int id= Integer.parseInt(request.getParameter("id"));
				postDao.deletePost(id, true);
				request.setAttribute("posts", postDao.getAllPosts(false, pageNumber, postPerPage));	
				request.setAttribute("paginations", postDao.getPaginationResult(false, postPerPage));
				template = TEMPLATE_LIST;
			}
			else if(action.equalsIgnoreCase("restore")) {
				int id= Integer.parseInt(request.getParameter("id"));
				postDao.deletePost(id, false);
				request.setAttribute("posts", postDao.getAllPosts(true, pageNumber, postPerPage));	
				request.setAttribute("paginations", postDao.getPaginationResult(true, postPerPage));
				template = TEMPLATE_LIST;
			}
		}			
		
		request.setAttribute("action", action);
		request.setAttribute("pageNumber", pageNumber);
		
		RequestDispatcher view = request.getRequestDispatcher(template);
		view.forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		propUtil = new PropUtil(getServletContext());
		
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		int resultId;
		String fileName = ""; 
		
		if(session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath()+"/admin/login");
		}
		else {
			Post post = new Post();
			User user = (User)session.getAttribute("user");
			post.setTitle(request.getParameter("title"));
			post.setSlug(request.getParameter("slug"));
			post.setContent(request.getParameter("content"));
			post.setSummary(request.getParameter("summary"));
			
			Part filePart = request.getPart("thumbnail"); // Retrieves <input type="file" name="thumbnail">
			
			fileName = ImageUtil.getFileName(filePart);
			if(fileName.equals("")){
				post.setImage(request.getParameter("currentThumbnail"));
			}
			else {
				post.setImage(fileName);				
			}
			// IF ID IS NULL THEN CREATE USER, OTHERWISE UPDATE USER
			if(id == null || id.isEmpty()) {
				post.setRegisterUserId(user.getId());
				post.setRegisterIp(request.getRemoteAddr());
				resultId = postDao.addPost(post);
				if(!fileName.equals("")){
					ImageUtil.saveFileToDirectory(filePart, propUtil);
				}
				session.setAttribute("message", "Create new post success!");
			} 
			else {
				post.setId(Integer.parseInt(id));
				post.setModificationUserId(user.getId());
				post.setModificationIp(request.getRemoteAddr());
				resultId = postDao.updatePost(post);
				if(!fileName.equals("")){
					ImageUtil.saveFileToDirectory(filePart, propUtil);
				}
				session.setAttribute("message", "Update '"+post.getTitle()+"' success!");
			}
			response.sendRedirect(request.getContextPath()+"/admin/news?action=edit&id="+resultId);			
		}	
	}
}
