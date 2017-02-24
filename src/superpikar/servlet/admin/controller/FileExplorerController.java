package superpikar.servlet.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superpikar.servlet.admin.model.MyFile;
import superpikar.servlet.util.PropUtil;

/**
 * Servlet implementation class FileExplorer
 */
public class FileExplorerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TEMPLATE_LIST = "/views/admin/file/file-list.jsp";
	private final String TEMPLATE_SINGLE = "/views/admin/file/file-single.jsp";
       
    public FileExplorerController() {
    }
    
    private String buildDirectoryString(String directory){
    	String results = "\\";
    	directory = directory==null? "": directory;
    	if(directory.split("/").length == 0){
    		return "\\";
    	}
    	else {
    		for (String val: directory.split("/")) {
    			results += "\\"+val;
    		}
    		return results;
    	}
    }
    
    private HashMap<String, String> splitDirectory(String directory){
    	HashMap<String, String> hmap = new HashMap<String, String>();
    	directory = directory==null? "": directory;
    	String[] strSplit = directory.split("/");
    	
    	if(directory == ""){	
    		// if in the root
    		return hmap;
    	}
    	else {
    		for(int i=0; i<strSplit.length; i++){
    			String path = "";
    			for(int j=0; j<=i; j++){
    				path += strSplit[j]+"/";	// i.e, {folder1-children: folder1/folder1-children}
    			}
    			System.out.println(path);
    			hmap.put(strSplit[i], path);
    		}
    		return hmap;    		
    	}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO still cannot handle folder with space or UTF-8 character
		PropUtil propUtil = new PropUtil(getServletContext());
		
		String page = "";
		String action = request.getParameter("action");
		String dir = request.getParameter("dir");
		String rebuildDir = propUtil.getProperty("UPLOAD_DIR")+buildDirectoryString(dir);		
		
		if(action=="edit"){
			page = TEMPLATE_SINGLE;
		}
		else{
			File rootDir = new File(rebuildDir);
			ArrayList<MyFile> files = new ArrayList<MyFile>();
			
			for(File file: rootDir.listFiles()){
				if(file.isDirectory()){
					files.add(new MyFile(file.getName(), file.getAbsolutePath(), file.isDirectory()));
					
				}
			}			
			for(File file: rootDir.listFiles()){
				if(file.isFile()){
					files.add(new MyFile(file.getName(), file.getAbsolutePath(), file.isDirectory()));					
				}
			}
			dir = dir==null? "" : dir+"/";	// formatting GET['dir'] for the next request 
			request.setAttribute("dir", dir);
			request.setAttribute("dirSplit", splitDirectory(dir));
			request.setAttribute("files", files);
			System.out.println("open: "+rebuildDir+" | path: "+dir+" | hashmap length: "+splitDirectory(dir).size());
			page = TEMPLATE_LIST;
		}
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
