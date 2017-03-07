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

public class HomeController extends HttpServlet {      
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE = "/views/admin/home/index.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if(action==null){
			RequestDispatcher view = request.getRequestDispatcher(TEMPLATE);
			view.forward(request, response);
		}
		else if(action.equalsIgnoreCase("logout")) {
			// DELETE THE SESSION >> SET MESSAGE LOGOUT SUCCESS >> GO TO LOGIN PAGE WITH MESSAGE
			session.removeAttribute("user");
			response.sendRedirect(request.getContextPath()+"/admin/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}
}
