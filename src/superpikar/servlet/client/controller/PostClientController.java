package superpikar.servlet.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import superpikar.servlet.admin.dao.CommentDao;
import superpikar.servlet.admin.dao.PostDao;
import superpikar.servlet.admin.model.Comment;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.admin.model.Post;
import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.ImageUtil;
import superpikar.servlet.util.PropUtil;

/**
 * Servlet implementation class PostController
 */
public class PostClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE_LIST = "/views/client/post/post-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/client/post/post-single.jsp";
	private PostDao postDao;
	private CommentDao commentDao;
	private PropUtil propUtil;
	private FilterAndSort filterAndSort = new FilterAndSort();
	
	public PostClientController() {
		postDao = new PostDao();
		commentDao = new CommentDao();
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = request.getParameter("id");
		String template = "";
    	
    	propUtil = new PropUtil(getServletContext());
    	String pageNumber = request.getParameter("page");
    	String postPerPage = propUtil.getProperty("client.postperpage");
		
    	if(id!=null){
    		// TODO add comment functionality
    		int postId = Integer.valueOf(id);
			request.setAttribute("post", postDao.getPostById(postId));
			request.setAttribute("comments", commentDao.getAllCommentByChannelId("post", postId, false));
			template = TEMPLATE_SINGLE;
		}
    	else {
    		request.setAttribute("posts", postDao.getAllPosts(false, pageNumber, postPerPage, filterAndSort));
    		template = TEMPLATE_LIST;
    	}
    	RequestDispatcher view = request.getRequestDispatcher(template);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String channelId = request.getParameter("channelId");
		int parentId = request.getParameter("parentId")==null||request.getParameter("parentId").equalsIgnoreCase("")? 0: Integer.valueOf(request.getParameter("parentId"));
		int resultId;
		
		if(session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath()+"/admin/news?id="+channelId);
		}
		else {
			Comment comment = new Comment();
			User user = (User)session.getAttribute("user");
			comment.setComment(request.getParameter("comment"));
			comment.setParentId(parentId);
			comment.setChannel(request.getParameter("channel"));
			comment.setChannelId(Integer.valueOf(channelId));
			comment.setRegisterUserId(user.getId());
			comment.setRegisterIp(request.getRemoteAddr());
			resultId = commentDao.addComment(comment);
			
			comment.setId(resultId);
			comment.setLineage(request.getParameter("lineage")+Integer.toString(resultId)+"/");
			commentDao.updateComment(comment);
			response.sendRedirect(request.getContextPath()+"/news?id="+channelId+"#comment-"+resultId);
		}
	}
}
