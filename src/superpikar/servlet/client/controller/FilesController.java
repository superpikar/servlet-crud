package superpikar.servlet.client.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superpikar.servlet.util.PropUtil;

/**
 * Servlet implementation class FilesController
 */
public class FilesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PropUtil propUtil;
	
	public FilesController(){
	}
    /**
     * credit : http://stackoverflow.com/questions/1812244/simplest-way-to-serve-static-data-from-outside-the-application-server-in-a-java
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getPathInfo().substring(1));
		String fileName = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		propUtil = new PropUtil(getServletContext());
		File file = new File(propUtil.getProperty("UPLOAD_DIR"), fileName);
		response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
		Files.copy(file.toPath(), response.getOutputStream());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
