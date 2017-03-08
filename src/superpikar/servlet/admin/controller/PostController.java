package superpikar.servlet.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
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
import superpikar.servlet.admin.model.FilterAndSort;
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
	private final String TEMPLATE_LIST = "/views/admin/post/post-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/admin/post/post-single.jsp";
	private HashMap<String, String> searchConditions = new HashMap<String, String>();
	private HashMap<String, String> sortColumns = new HashMap<String, String>();
	private FilterAndSort filterAndSort = new FilterAndSort();
	
	public PostController(){
		postDao = new PostDao();
		
		searchConditions.put("title", "Title");
		searchConditions.put("content", "Content");
		sortColumns.put("title", "Title");
		sortColumns.put("registerDate", "Reg. Date");
		
		filterAndSort.setSearchConditions(searchConditions);
		filterAndSort.setSortColumns(sortColumns);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		propUtil = new PropUtil(getServletContext());
		String action = request.getParameter("action")==null? "list": request.getParameter("action");
		String template = "";
		
		if(action.equalsIgnoreCase("add")||action.equalsIgnoreCase("edit")) {
			if(action.equalsIgnoreCase("edit")) {
				int id= Integer.parseInt(request.getParameter("id"));
				request.setAttribute("post", postDao.getPostById(id));
			}
			template = TEMPLATE_SINGLE;
		} 
		else {
			String pageNumber = request.getParameter("page");
			String postPerPage = propUtil.getProperty("backend.news.itemsperpage");
			boolean showIsDelete = false;
			
			filterAndSort.setCondition(request.getParameter("condition"));
			filterAndSort.setKeyword(request.getParameter("keyword"));
			filterAndSort.setSortColumn(request.getParameter("sortColumn"));
			filterAndSort.setSortOrder(request.getParameter("sortOrder"));
			
			if(action.equalsIgnoreCase("list")){
				session.removeAttribute("message");	// clear session
				showIsDelete = false;
			}
			else if(action.equalsIgnoreCase("trash")){
				showIsDelete = true;				
			}
			else if(action.equalsIgnoreCase("delete")){
				int id= Integer.parseInt(request.getParameter("id"));
				postDao.deletePost(id, true);
				showIsDelete = false;
			}
			else if(action.equalsIgnoreCase("restore")){
				int id= Integer.parseInt(request.getParameter("id"));
				postDao.deletePost(id, false);
				showIsDelete = true;
			}
			
			if(filterAndSort.getCondition()!=null){
				request.setAttribute("searchQueryString", "&condition="+filterAndSort.getCondition()+"&keyword="+filterAndSort.getKeyword());
			}
			
			if(filterAndSort.getSortColumn()!=null){
				request.setAttribute("sortQueryString", "&sortColumn="+filterAndSort.getSortColumn()+"&sortOrder="+filterAndSort.getSortOrder());
			}
			
			request.setAttribute("filterAndSort", filterAndSort);
			request.setAttribute("posts", postDao.getAllPosts(showIsDelete, pageNumber, postPerPage, filterAndSort));
			request.setAttribute("paginations", postDao.getPaginationResult(showIsDelete, pageNumber, postPerPage, filterAndSort));
			template = TEMPLATE_LIST;
		}
		
		request.setAttribute("action", action);		
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
