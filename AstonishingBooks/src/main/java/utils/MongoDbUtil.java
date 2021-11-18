package utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import mongobusiness.Book;
import mongobusiness.Cart;
import mongobusiness.Payment;
import mongobusiness.Test;
import mongobusiness.User;
import utils.Constants;

public class MongoDbUtil {

	private List<Book> allBooks;
	
	public MongoDbUtil () {
		allBooks = new ArrayList<Book>();
	}
	/**
	 * Create or update an existing user.
	 * @param entity     
	 * @param mongoOperations
	 * */
	public User SaveOrUpdateUser(User user, List<String> bookNames, MongoTemplate mongoOperation) {
		
		// Create query
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(user.getFirstname()));
		User dbUser = this.GetUserByEmail(user.getEmail(), mongoOperation);
		
		if (dbUser != null) {
			user = dbUser;
		}
		
		// If user has selected books
		if (bookNames != null && bookNames.size() != 0) {
			
			// Get list of books 
			allBooks = this.findAllBooks(mongoOperation);
			
			// Add selected book names to book list
			List<Book> books = new ArrayList<Book>();
			for(Book book : allBooks) {
				if (bookNames.contains(book.getName())) {
					books.add(book);
				}
			}
			
			// Create cart
			Cart cart = new Cart();
			
			// Set user name, book list, and order date
			cart.setUsername(user.getUsername());
			cart.setEmail(user.getEmail());
			cart.setBooks(books);
			cart.setOrderdate(new DateUtil().GetDateTimeNow());
			
			// Save cart and add to user
			user.setCart(this.SaveOrUpdateCart(cart, user.getEmail(), mongoOperation));
		}
		
		// If payment info is present
		if (user.getPayment() != null) {
			
			user.setPayment(this.SaveOrUpdatePayment(user.getPayment(), user.getEmail(), mongoOperation));
		}
		
		// Save user to DB
		mongoOperation.save(user);
		
		//get the updated object again
		return mongoOperation.findOne(query, User.class);
	}
	
	/**
	 * Save or update cart information
	 * @param cart
	 * @param username
	 * @param mongoOperation
	 * @return
	 */
	public Cart SaveOrUpdateCart(Cart cart, String email, MongoTemplate mongoOperation) {
				
		// Create query
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
				
		// Find existing cart information
		Cart dbCart = mongoOperation.findOne(query, Cart.class);
		
		// If cart does not exists
		if (dbCart == null) {
			// Assign cart to dbCart
			dbCart = cart;
		}
		else {
			// Else
			// Update current info
			dbCart.setOrderdate(new DateUtil().GetDateTimeNow());
			dbCart.setBooks(cart.getBooks());	
		}
		
		// Save to databse
		mongoOperation.save(dbCart);
		
		// Find user
		User user = this.GetUserByEmail(email, mongoOperation);
		
		// If user exists
		if (user != null) {
			// Update info and save to database
			user.setCart(dbCart);
			mongoOperation.save(user);
		} 
		
		return mongoOperation.findOne(query, Cart.class);
	}
	
	/**
	 * Save or update payment information
	 * @param payment
	 * @param username
	 * @param mongoOperation
	 * @return
	 */
	public Payment SaveOrUpdatePayment(Payment payment, String email, MongoTemplate mongoOperation) {
		
		try {
			// Create query
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email).andOperator(Criteria.where("cardname").is(payment.getCardname())));
			
			// Find existing payment information
			Payment dbPayment = mongoOperation.findOne(query, Payment.class);
			
			
			// If Payment info exists in DB
			if (dbPayment != null) {
				// Update current payment info
				dbPayment.setCardname(payment.getCardname());
				dbPayment.setCardNumber(payment.getCardNumber());
				dbPayment.setCardType(payment.getCardType());
				dbPayment.setExperiationMonth(payment.getExperiationMonth());
				dbPayment.setExperiationYear(payment.getExperiationYear());
			}
			else {
				// Else
				// Assign payment to dbPayment
				dbPayment = payment;
			}
			
			// Save payment to db
			mongoOperation.save(dbPayment);
			
			// Query user by email
			User user = this.GetUserByEmail(email, mongoOperation);
			
			// If user exists
			if (user != null) {
				// Set payment and save to DB
				user.setPayment(dbPayment);;
				mongoOperation.save(user);
			}
			
			// Return payment information
			return mongoOperation.findOne(query, Payment.class);
		}
		catch (Exception e) {
			System.out.println("An error has occured");
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * Updates the first document that matches the query.Updates the first document that matches the query.
	 * @param entity
	 * @param mongoOperation
	 * @param name
	 */
	public Test UpdateFirst(Test entity, MongoTemplate mongoOperation, String name, String field, String value) {
		// Instantiate query object
		// and add criteria
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		
		// Find existing document
		Test dbTest = mongoOperation.findOne(query, Test.class);
		
		// Instantiate update object
		Update update = new Update();
		
		// Update field
		// TODO: Maybe create dictionary to store field and value
		update.set("field", value);
		
		// Save to DB
		mongoOperation.save(dbTest);

		//get the updated object again
		return mongoOperation.findOne(query, Test.class);
	}
	
	/**
	 * Find User in DB by email
	 * @param user
	 * @param mongoOperation
	 * @return
	 */
	public User GetUserByEmail(String email, MongoTemplate mongoOperation) {
		
		// Query
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		
		// Find existing User
		return mongoOperation.findOne(query, User.class);
	}
	
	/**
	 * Remove book from bookstore.  Only for Admins
	 * @param name
	 * @param mongoOperation
	 */
	public void AddOrDeleteBook(String action, Book book, MongoTemplate mongoOperation){
		
		Query query = new Query(Criteria.where("name").is(book.getName()));
		switch (action)
		{
			case Constants.ADD:
				 mongoOperation.save(book);
				 this.allBooks.add(book);
				break;
			case Constants.DELETE:
				mongoOperation.remove(query, Book.class);
				break;
			default:
				System.out.println("Could not determine action.");
			break;
			
		}
	}
	

	/**
	 * Returns a list of all books.
	 * @param mongoOperation
	 * @return
	 */
	public List<Book> findAllBooks(MongoTemplate mongoOperation){
		return mongoOperation.findAll(Book.class);
	}
	
	
}