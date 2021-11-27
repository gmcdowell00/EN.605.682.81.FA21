package contextlisteners;



import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;

import controller.BookHelper;

import com.mongodb.client.MongoClient;
import mongobusiness.Book;
import utils.Constants;


public class DbContextListener implements ServletContextListener {

	private static final String DATABASE_URL = "mongodb+srv://admin:admin@sandbox.fh9hs.mongodb.net/astonishingbooks?retryWrites=true&w=majority";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			// TODO Auto-generated method stub
			System.out.println("*****Starting Applicaiton*****");
			ServletContext context = event.getServletContext();
			ConnectionString connectionString = new ConnectionString(DATABASE_URL);
			MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
					.build();
			System.out.println("Initialzing databse");
			MongoClient client = MongoClients.create(mongoClientSettings);
			MongoTemplate ops = new MongoTemplate(client, "astonishingbooks");			
			System.out.println("Databse initalized");
			
			System.out.println("Querying books");
			Query query = new Query();
//			query.limit(12);                // return all of the books so they can be sorted by date
			
			List<Book> books = ops.find(query, Book.class);
			System.out.println("Queried " + books.size() + " books" );
			
			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();
			
			// sort the books by date, return the 12 newest that are not magazines
			List<Book> sortedBooks = bookHelper.newestBooks(books);

//			context.setAttribute(Constants.BOOKS, books);
			context.setAttribute(Constants.BOOKS, sortedBooks);  // make the sorted books available for display
			context.setAttribute(Constants.DATABASE, ops);
		} catch (Exception e) {
			System.out.println("An error has occured");
			System.out.println("Error: " + e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

}
