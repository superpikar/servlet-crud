package superpikar.servlet.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/FileUploadError")
public class FileUploadError extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @description
	 * 		ServletInputStream object allows for large files to be handled equally well as files of a small size. 
	 * 		Determines the delimiter and keeps parsing until the end of the request, when the delimiter with '--' appended is found.
	 * 		If the name="file", then the content is saved are saved as file. Any other name is treated as a form parameter and echoed back in the response.
	 * 		Determine the name of the file by searching filename="name-of-the-file", then create /files directory
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<html>"
				+ 		"<head><title>Upload File</title></head>"
				+ 		"<body>"
				+ 			"File upload success. <a href=\'/servlet-crud/files/'>Click here to browse through all uploaded files</a></br>"
				+ 		"</body>");
		
		ServletInputStream sis = request.getInputStream();
		StringWriter sw = new StringWriter();
		int i = sis.read();
		for(;i!=-1&&i!='\r';i=sis.read()){
			sw.write(i);
		}
		sis.read();	// ditch '\n'
		String delimiter = sw.toString();
		
		int count = 0;
		while (true) {
			StringWriter h = new StringWriter();
			int[] temp = new int[4];
			temp[0] = (byte)sis.read();
			temp[1] = (byte)sis.read();
			temp[2] = (byte)sis.read();
			
			h.write(temp[0]);
			h.write(temp[1]);
			h.write(temp[2]);
			
			// read reader
			for(temp[3]=sis.read(); temp[3]!=-1; temp[3]=sis.read()) {
				if(temp[0] == '\r' && temp[1] == '\r' && temp[2] == '\r' && temp[3] == '\n') {
					break;
				}
				h.write(temp[3]);
				temp[0] = temp[1];
				temp[1] = temp[2];
				temp[2] = temp[3];
			}
			String header = h.toString();
			
			int startName = header.indexOf("name=\"");
			int endName = header.indexOf("\"", startName+6);
			if(startName == -1 || endName == -1) {
				break;
			}
			
			String name = header.substring(startName+6, endName);
			if(name.equals("file")){
				startName = header.indexOf("filename=\"");
				endName = header.indexOf("\"", startName+10);
				String filename = header.substring(startName+10, endName);
				ServletContext sc = request.getSession().getServletContext();
				
				File file = new File(sc.getRealPath("/files"));
				file.mkdirs();
				FileOutputStream fos = new FileOutputStream(sc.getRealPath("/files")+"/"+filename);
				
				// write whole file to disk
				int length = delimiter.length();
//				delimiter = "\r\n" + delimiter;
				byte[] body = new byte[delimiter.length()];
				for(int j=0; j<body.length; j++){
					body[j] = (byte)sis.read();
					fos.write(body[j]);
				}
				
				// check it wasn't at 0 length file
				if(!delimiter.equals(new String(body))) {
					int e = body.length-1;
					i = sis.read();
					for(;i!=-1;i=sis.read()){
//						fos.write(body[0]);
						for(int l=0; l <body.length-1; i++){
							body[l] = body[l+1];
						}
						body[e] = (byte)i;
						fos.write(body[e]);
						if(delimiter.equals(new String(body))) {
							break;
						}
						length++;
					}
				}
				fos.flush();
				fos.close();
			}
			if(sis.read()=='-' && sis.read()=='-'){
				break;
			}
		}		
		out.println("</html>");
	}

}
