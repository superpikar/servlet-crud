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
import superpikar.servlet.admin.dao.UserDao;
import superpikar.servlet.admin.model.Post;
import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.ImageUtil;
import superpikar.servlet.util.PropUtil;

/**
 * Servlet implementation class PostController
 */
@MultipartConfig
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private PropUtil propUtil;
	private final String TEMPLATE_LIST = "/views/admin/user/user-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/admin/user/user-single.jsp";
	
	public UserController(){
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		propUtil = new PropUtil(getServletContext());
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String page = "";
		
		if(action==null){
			session.removeAttribute("message");	// clear session
			request.setAttribute("users", userDao.getAllUsers(false));			
			page = TEMPLATE_LIST;
		}
		else {
			if(action.equalsIgnoreCase("trash")) {
				request.setAttribute("users", userDao.getAllUsers(true));			
				page = TEMPLATE_LIST;
			}
			else if(action.equalsIgnoreCase("add")) {
				page = TEMPLATE_SINGLE;
			}
			else if(action.equalsIgnoreCase("edit")) {
				int id= Integer.parseInt(request.getParameter("id"));
				request.setAttribute("user", userDao.getUserById(id));
				page = TEMPLATE_SINGLE;
			}
			else if(action.equalsIgnoreCase("delete")) {
				int id= Integer.parseInt(request.getParameter("id"));
				userDao.deleteUser(id, true);
				request.setAttribute("users", userDao.getAllUsers(false));	
				page = TEMPLATE_LIST;
			}
			else if(action.equalsIgnoreCase("restore")) {
				int id= Integer.parseInt(request.getParameter("id"));
				userDao.deleteUser(id, false);
				request.setAttribute("users", userDao.getAllUsers(true));	
				page = TEMPLATE_LIST;
			}
		}			
		request.setAttribute("action", action);
		request.setAttribute("userRoles", propUtil.getPropertyAsArray("roles.groups"));
		RequestDispatcher view = request.getRequestDispatcher(page);
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
			User user = new User();
			User adminUser = (User)session.getAttribute("user");
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setEmail(request.getParameter("email"));
			user.setWebsite(request.getParameter("website"));
			user.setRole(request.getParameter("role"));
			
			Part filePart = request.getPart("thumbnail"); // Retrieves <input type="file" name="thumbnail">
			
			fileName = ImageUtil.getFileName(filePart);
			if(fileName.equals("")){
				user.setImage(request.getParameter("currentThumbnail"));
			}
			else {
				user.setImage(fileName);				
			}
			// IF ID IS NULL THEN CREATE USER, OTHERWISE UPDATE USER
			if(id == null || id.isEmpty()) {
				user.setRegisterUserId(adminUser.getId());
				user.setRegisterIp(request.getRemoteAddr());
				resultId = userDao.addUser(user);
				if(!fileName.equals("")){
					ImageUtil.saveFileToDirectory(filePart, propUtil);
				}
				session.setAttribute("message", "Create new user success!");
			} 
			else {
				user.setId(Integer.parseInt(id));
				user.setModificationUserId(adminUser.getId());
				user.setModificationIp(request.getRemoteAddr());
				resultId = userDao.updateUser(user);
				if(!fileName.equals("")){
					ImageUtil.saveFileToDirectory(filePart, propUtil);
				}
				session.setAttribute("message", "Update '"+user.getUsername()+"' success!");
			}
			response.sendRedirect(request.getContextPath()+"/admin/user?action=edit&id="+resultId);			
		}	
	}
}
