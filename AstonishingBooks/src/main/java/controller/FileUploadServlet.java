package controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.imageio.ImageIO;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.output.*;

import utils.Constants;

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

		File file;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;

		ServletContext context = getServletContext();

		String appPath = (String) context.getAttribute(Constants.FILEUPLOADPATH);

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

						if (!file.getName().contains(".jpg") && request.getServerName().contains("dev8")) {
							Path target = Paths.get(appPath + file.getName() + ".jpg");

							BufferedImage originalImage = ImageIO.read(file);
							// create a blank, RGB, same width and height
							BufferedImage newBufferedImage = new BufferedImage(originalImage.getWidth(),
									originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

							// draw a white background and puts the originalImage on it.
							newBufferedImage.createGraphics().drawImage(originalImage, 0, 0, java.awt.Color.WHITE,
									null);

							// save an image
							ImageIO.write(newBufferedImage, "jpg", target.toFile());
						} else {
							fi.write(file);
						}

					}
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} else {

			RequestDispatcher dispatcher = getServletConfig().getServletContext()
					.getRequestDispatcher("/error_page.jsp");
			dispatcher.forward(request, response);
		}

		RequestDispatcher dispatcher = getServletConfig().getServletContext()
				.getRequestDispatcher("/admin_book_info.jsp");
		dispatcher.forward(request, response);
	}

}
