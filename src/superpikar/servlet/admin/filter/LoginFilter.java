package superpikar.servlet.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.PropUtil;

/**
 * Servlet Filter implementation class LoginFilter
 * tutorial about filter : 
 * 	- http://stackoverflow.com/questions/13274279/authentication-filter-and-servlet-for-login
 *  - http://tutorials.jenkov.com/java-servlets/servlet-filters.html
 */
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res= (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		PropUtil propUtil = new PropUtil(request.getServletContext());
		String loginURI = req.getContextPath() + "/admin/login";
		String homeURI = req.getContextPath() + "/admin";
		String requestURI = req.getRequestURI();
		String requestURIandParam = req.getRequestURI()+"?"+req.getQueryString();
		System.out.println("user tried to access "+requestURI);
		
		boolean isLoggedIn = session != null && session.getAttribute("user")!=null;
		boolean isLoginRequest = requestURI.equals(loginURI);
		
		if(isLoginRequest && !isLoggedIn){
			chain.doFilter(req, res);	
		}
		else if(isLoggedIn) {
			if(isLoginRequest){
				res.sendRedirect(homeURI);
			}
			else {
				User user = (User)session.getAttribute("user");
				String cleanURI = requestURI.replaceAll(req.getContextPath()+"/", ""); // convert /servlet-crud/admin/login >> admin/login
				String cleanURIandParam = requestURIandParam.replaceAll(req.getContextPath()+"/", "");
				
				// SET cleanURI session attribute for active class in the sidebar menu of template
				if(req.getQueryString()==null){
					session.setAttribute("cleanURI", cleanURI);
					session.setAttribute("cleanPath", cleanURI);
				}
				else {
					session.setAttribute("cleanURI", cleanURIandParam);
					session.setAttribute("cleanPath", cleanURI);
				}
				
				if(isUserAllowed(user, propUtil, cleanURI)){
					chain.doFilter(req, res);
				}
				else {
					res.sendRedirect(loginURI);
				}				
			}
		}
		else {
			res.sendRedirect(loginURI);
		}
	}
	
	private boolean isUserAllowed(User user, PropUtil propUtil, String requestURI) throws IOException{
		System.out.println("user logged in tried to access "+requestURI);
		String[] requestURIs = requestURI.split("/");
		boolean isAllowed = false;
		
		if(requestURIs.length>1){
			String[] rolesInPostType = propUtil.getProperty("backend."+requestURIs[1]+".roles").split(",");
			for(String role: rolesInPostType){
				if(user.getRole().equalsIgnoreCase(role)){
					isAllowed = true;
				}
			}			
		}
		else {
			isAllowed = true; 
		}
		return isAllowed;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	
	public void destroy() {
	}

}
