package contextlisteners;



import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
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
			List<Book> books = ops.findAll(Book.class);
			System.out.println("Queried " + books.size() + " books" );

			context.setAttribute(Constants.BOOKS, books);
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
