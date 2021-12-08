package contextlisteners;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;

import controller.BookHelper;

import com.mongodb.client.MongoClient;
import mongobusiness.Book;
import mongobusiness.Cart;
import mongobusiness.Payment;
import mongobusiness.User;
import utils.Constants;
import utils.MongoDbUtil;


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
			Criteria criteria = new Criteria();
			
//			criteria.andOperator(
//		            Criteria.where("name").ne(null),
//		            Criteria.where("publishedDate").ne(null),
//		            Criteria.where("description").ne(null));
			
			Query query = new Query(criteria);
			
			List<Book> books = ops.find(query, Book.class);
			System.out.println("Queried " + books.size() + " books" );
			
			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();
			
			// sort the books by date, return the 12 newest that are not magazines
			List<Book> sortedBooks = bookHelper.newestBooks(books, ops);

			// set the sorted list of books as a context attribute
			context.setAttribute(Constants.BOOKS, sortedBooks);  
			
			// make the database available to the whole app
			context.setAttribute(Constants.DATABASE, ops);
			
			// create a mongo utility object
			MongoDbUtil mongoUtil = new MongoDbUtil();
			
			// get the guest user
			User guestUser = mongoUtil.GetUserByID("61aa68b78a5d542ac9f844b9", ops);
			
			// set all guest user fields to empty
			guestUser.setFirstname("");
			guestUser.setLastname("");
			guestUser.setAddress("");
			guestUser.setCity("");
			guestUser.setState("");
			guestUser.setCountry("");
			guestUser.setZip("");
			
			// set the guest payment fields to empty
			Payment guestPayment = guestUser.getPayment();		
			guestPayment.setCardname(" ");
			guestPayment.setCardNumber(" ");
			guestPayment.setExperiationMonth(0);
			guestPayment.setExperiationYear(0);
			guestPayment.setCardType(null);
			
			// set the empty payment object in the guest user
			guestUser.setPayment(guestPayment);
			
			// create an empty list of book objects
			List<Book> emptyList = new ArrayList<>();
			
			// set the guest cart fields to empty
			Cart guestCart = guestUser.getCart();
			guestCart.setOrderdate(null);
			guestCart.setBooks(emptyList);
			
			// set the empty cart object in the guest user
			guestUser.setCart(guestCart);
			
			// set the wishlist to an empty list
			guestUser.setWishlist(emptyList);
			
			// save the empty guest user to the database
			User updatedGuestUser = mongoUtil.SaveOrUpdateUser(guestUser, ops);

			// make the empty guest user available to the app
			context.setAttribute(Constants.USER, updatedGuestUser);
					
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
