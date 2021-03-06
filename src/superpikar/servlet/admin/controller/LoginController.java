package superpikar.servlet.admin.controller;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jtwig.web.servlet.JtwigRenderer;

import superpikar.servlet.admin.dao.PostDao;
import superpikar.servlet.admin.dao.UserDao;
import superpikar.servlet.admin.model.User;

/**
 * Servlet implementation class ServletCRUD
 */

public class LoginController extends HttpServlet {      
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private final String TEMPLATE_LOGIN = "/views/admin/login/login.jsp";
	
	public LoginController(){
		 userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") != null){
			response.sendRedirect(request.getContextPath()+"/admin");			
		}
		else{
			session.removeAttribute("message");
			String fromURI = request.getParameter("from");
			request.setAttribute("from", fromURI);
			RequestDispatcher view = request.getRequestDispatcher(TEMPLATE_LOGIN);
			view.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fromURI = request.getParameter("from");		// for redirecting back to a page after login, credit http://stackoverflow.com/questions/1921230/redirect-back-to-a-page-after-a-login
		
		User result = userDao.getUserByUsernamePassword(username, password);
		System.out.println(result.getUsername()+" "+result.getPassword());
		
		if(!result.isEmpty()){
			session.setAttribute("message", "Login success");
			session.setAttribute("user", result);
			
			if(fromURI==null){
				response.sendRedirect(request.getContextPath()+"/admin");				
			}
			else {
				if(fromURI.equalsIgnoreCase("")){
					response.sendRedirect(request.getContextPath()+"/admin");
				}
				else {
					// redirect back to the referer page
					response.sendRedirect(fromURI);					
				}
			}
			
		}
		else {
			System.out.println("null!");
			session.setAttribute("message", "Username/password wrong");
			RequestDispatcher view = request.getRequestDispatcher(TEMPLATE_LOGIN);
			view.forward(request, response);
		}
	}

}
