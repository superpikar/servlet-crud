package superpikar.servlet.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Servlet implementation class ConcurrentUser
 */
public class ConcurrentUserTracker implements HttpSessionListener {
	static int users = 0;
       
    public void sessionCreated(HttpSessionEvent e) {
    	users++;
    }

	public void sessionDestroyed(HttpSessionEvent arg0) {
		users--;		
	}
	public static int getConcurrentUsers() {
		return users;
	}
}
