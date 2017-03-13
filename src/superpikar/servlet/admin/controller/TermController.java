package superpikar.servlet.admin.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import superpikar.servlet.admin.dao.TermDao;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.admin.model.Term;
import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.PropUtil;
import superpikar.servlet.util.TextUtil;

/**
 * Servlet implementation class PostController
 */
@MultipartConfig
public class TermController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TermDao termDao;
	private PropUtil propUtil;
	private final String TEMPLATE_LIST = "/views/admin/term/term-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/admin/term/term-single.jsp";
	private HashMap<String, String> searchConditions = new HashMap<String, String>();
	private HashMap<String, String> sortColumns = new HashMap<String, String>();
	private FilterAndSort filterAndSort = new FilterAndSort();
	
	public TermController(){
		termDao = new TermDao();
		
		searchConditions.put("name", "Name");
		searchConditions.put("description", "Description");
		sortColumns.put("name", "Username");
		sortColumns.put("registrationDate", "Email");
		sortColumns.put("lineage", "Lineage");
		
		filterAndSort.setSearchConditions(searchConditions);
		filterAndSort.setSortColumns(sortColumns);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		propUtil = new PropUtil(getServletContext());
		String action = request.getParameter("action")==null? "list" : request.getParameter("action");
		String template = "";		
		
		if(action.equalsIgnoreCase("add")||action.equalsIgnoreCase("edit")) {
			if(action.equalsIgnoreCase("edit")) {
				int id= Integer.parseInt(request.getParameter("id"));
				request.setAttribute("user", termDao.getTermById(id));
			}
			template = TEMPLATE_SINGLE;
			request.setAttribute("taxonomies", propUtil.getPropertyAsArray("backend.taxonomies"));
		}
		else {
			String taxonomy = request.getParameter("taxonomy")==null? "tag": request.getParameter("taxonomy");
			String postPerPage = propUtil.getProperty("backend.term.itemsperpage");
			String pageNumber = request.getParameter("page");
			boolean showIsDelete = false;
			
			filterAndSort.setConditionDefault("taxonomy");	// SET DEFAULT SEARCH FOR TAXONOMY
			filterAndSort.setKeywordDefault(taxonomy);
			filterAndSort.setCondition(request.getParameter("condition"));
			filterAndSort.setKeyword(request.getParameter("keyword"));
			filterAndSort.setSortColumn(request.getParameter("sortColumn"));
			filterAndSort.setSortOrder(request.getParameter("sortOrder"));
			
			if(action.equalsIgnoreCase("list")){
				session.removeAttribute("message");	// clear session
				showIsDelete = false;
			}
			else if(action.equalsIgnoreCase("trash")) {
				showIsDelete = true;
			}
			else if(action.equalsIgnoreCase("delete")) {
				int id= Integer.parseInt(request.getParameter("id"));
				termDao.deleteTerm(id, true);
				showIsDelete = false;
			}
			else if(action.equalsIgnoreCase("restore")) {
				int id= Integer.parseInt(request.getParameter("id"));
				termDao.deleteTerm(id, false);
				showIsDelete = true;
			}
			
			if(filterAndSort.getCondition()!=null){
				request.setAttribute("searchQueryString", "&condition="+filterAndSort.getCondition()+"&keyword="+filterAndSort.getKeyword());
			}
			
			if(filterAndSort.getSortColumn()!=null){
				request.setAttribute("sortQueryString", "&sortColumn="+filterAndSort.getSortColumn()+"&sortOrder="+filterAndSort.getSortOrder());
			}
			
			request.setAttribute("filterAndSort", filterAndSort);
			request.setAttribute("taxonomy", taxonomy);
			request.setAttribute("title", TextUtil.capitalize(taxonomy));
			request.setAttribute("terms", termDao.getAllTerms(showIsDelete, pageNumber, postPerPage, filterAndSort));	
			request.setAttribute("paginations", termDao.getPaginationResult(showIsDelete, pageNumber, postPerPage, filterAndSort));
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
		String taxonomy = request.getParameter("taxonomy");
		int resultId;
		
		if(session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath()+"/admin/login");
		}
		else {
			Term term = new Term();
			User adminUser = (User)session.getAttribute("user");
			term.setName(request.getParameter("name"));
			term.setSlug(request.getParameter("slug"));
			term.setLineage(request.getParameter("lineage"));
			term.setTaxonomy(taxonomy);
			
			// IF ID IS NULL THEN CREATE USER, OTHERWISE UPDATE USER
			if(id == null || id.isEmpty()) {
				term.setRegisterUserId(adminUser.getId());
				term.setRegisterIp(request.getRemoteAddr());
				resultId = termDao.addTerm(term);
				
				session.setAttribute("message", "Create new term success!");
			} 
			else {
				term.setId(Integer.parseInt(id));
				term.setModificationUserId(adminUser.getId());
				term.setModificationIp(request.getRemoteAddr());
				resultId = termDao.updateTerm(term);
				session.setAttribute("message", "Update '"+term.getName()+"' success!");
			}
			response.sendRedirect(request.getContextPath()+"/admin/term?action=edit&taxonomy="+taxonomy+"&id="+resultId);
		}	
	}
}
