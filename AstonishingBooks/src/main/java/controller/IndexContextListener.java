package controller;

import javax.servlet.*;
import java.util.*;


public class IndexContextListener implements ServletContextListener
{

	@Override
	public void contextInitialized(ServletContextEvent event) 
	{
		// 
		
		ServletContext sc = event.getServletContext();
		
		// Get the initial list of books to display on the index page
		//     BookDBWorker BookIO = new BookDBWorker();      // need a DB worker object of some kind?
		//     ArrayList<Book> initialBooks = BookIO.getInitialBooks();  // get an ArrayList of book objects

		// add the ArrayList as a ServletContext attribute
		//     sc.setAttribute("initialBooks", initialBooks);
		
		System.out.println("Application is starting...");
	}
	
	

	@Override
	public void contextDestroyed(ServletContextEvent event) 
	{
		// Any cleanup necessary when the app shuts down?	
	}

}
