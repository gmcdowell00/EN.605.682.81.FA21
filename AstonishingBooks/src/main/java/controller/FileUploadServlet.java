package controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.output.*;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// get the session object
		HttpSession session = request.getSession();

		// Get book info
		String title = request.getParameter("name");
		String author = request.getParameter("author");
		String date = request.getParameter("publishDate");
		String genre = request.getParameter("genre");
		String price = request.getParameter("price");
		String description = request.getParameter("description");

		File file;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		// ServletContext context = pageContext.getServletContext();
		// String filePath = getServletContext().getRealPath(/files);
		ServletContext context = getServletContext();
		
		//String rpath = "src/main/webapp/coverImages/";
		String rpath = "/src/main/webapp/coverImages/";
		//String appPath = context.getRealPath(request.getContextPath());
		String appPath = "C:\\Users\\GMcDo\\git\\EN.605.682.81.FA21\\AstonishingBooks\\src\\main\\webapp\\coverImages\\";//request.getContextPath() + rpath; 

		//String path = appPath + rpath;
		// Verify the content type
		String contentType = request.getContentType();

		if ((contentType.indexOf("multipart/form-data") >= 0)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);

			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File("c:\\temp"));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// maximum file size to be uploaded.
			upload.setSizeMax(maxFileSize);

			try {
				// Parse the request to get file items.
				List fileItems = upload.parseRequest(request);

				// Process the uploaded file items
				Iterator i = fileItems.iterator();
				/*
				 * out.println("<html>"); out.println("<head>");
				 * out.println("<title>JSP File upload</title>"); out.println("</head>");
				 * out.println("<body>");
				 */
				
				  String webappRoot = context.getRealPath("/");
				  
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (!fi.isFormField()) {
						// Get the uploaded file parameters
						String fieldName = fi.getFieldName();
						String fileName = fi.getName();
						boolean isInMemory = fi.isInMemory();
						long sizeInBytes = fi.getSize();

						// Write the file
						if (fileName.lastIndexOf("\\") >= 0) {
							file = new File(appPath + fileName.substring(fileName.lastIndexOf("\\")));
						} else {
							file = new File(appPath + fileName.substring(fileName.lastIndexOf("\\") + 1));
						}
						fi.write(file);
						// out.println("Uploaded Filename: " + filePath +
						// ileName + "<br>");
					}
				}
				// out.println("</body>");
				// out.println("</html>");
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} else {
			/*
			 * out.println("<html>"); out.println("<head>");
			 * out.println("<title>Servlet upload</title>"); out.println("</head>");
			 * out.println("<body>"); out.println("<p>No file uploaded</p>");
			 * out.println("</body>"); out.println("</html>");
			 */
		}

		RequestDispatcher dispatcher = getServletConfig().getServletContext()
				.getRequestDispatcher("/admin_book_info.jsp");
		dispatcher.forward(request, response);
		// doGet(request, response);
	}

}
