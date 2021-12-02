package controller;

// default imports
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// additional imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import javax.mail.MessagingException;   // need to add mail jar
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Query;

import mongobusiness.Book;
import mongobusiness.Cart;
import mongobusiness.Payment;
import mongobusiness.User;
import utils.Constants;
import utils.MongoDbUtil;

/**
 * Servlet implementation class AstonishingServlet
 */
@WebServlet("/AstonishingServlet")
public class AstonishingServlet extends HttpServlet {

	// anyone know what this line does?
	private static final long serialVersionUID = 1L;

	// all that doGet does is call doPost
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// doPost processes requests and redirects based on the submitted action and
	// other inputs
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// declare a variable to hold the forwarding url
		String url = "/index.jsp";

		// extract values from the request object
		String action = request.getParameter("action");

		// get the session object
		HttpSession session = request.getSession();
		
		// get the context object
		ServletContext context = getServletContext();
		
		// create a mongo template
		MongoTemplate ops = (MongoTemplate) context.getAttribute(Constants.DATABASE);
		
		// create a mongo utility object
		MongoDbUtil mongoUtil = new MongoDbUtil();

		// pull books from the database and redirect to the landing page
		if (action.equals("goToHome")) {		
			
			// pull the new books and redirect to the index page
			url = newBooksIndex(ops, context);
		} else if (action.equals("goToFiction") || action.equals("goToNonFiction") || 
				action.equals("goToMagazine") || action.equals("goToReference")) {
			
			// create a new query 
			Query query = new Query();

			// return all of the books
			List<Book> books = ops.find(query, Book.class);
			
			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();
			
			// set the genre string and url
			String genreString = "";
			if (action.equals("goToFiction")) {
				genreString = "Fiction";
				url = "/fiction_page.jsp";
			} else if (action.equals("goToNonFiction")) {
				genreString = "Non-Fiction";
				url = "/nonfiction_page.jsp";
			} else if (action.equals("goToMagazine")) {
				genreString = "Magazine";
				url = "/magazine_page.jsp";
			} else if (action.equals("goToReference")) {
				genreString = "Reference";
				url = "/reference_page.jsp";
			}
			
			// sort the books by genre
			List<Book> sortedBooks = bookHelper.searchBooks(books, genreString);

			// make the sorted books available for display
			context.setAttribute(Constants.BOOKS, sortedBooks);  			
		} else if (action.equals("showBookInfo")) {
			// get the ID of the book to display
			String bookID = request.getParameter("bookId");
			
			// create a new query 
			Query query = new Query();

			// return all of the books
			List<Book> books = ops.find(query, Book.class);
			
			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();
			
			// get the selected book
			Book foundBook = bookHelper.bookById(books, bookID);

			// make the selected book available for display
			context.setAttribute(Constants.BOOK, foundBook);  
			
			// set the url for the book info page
			url = "/book_info.jsp";
		} else if (action.equals("search")) {
						
			// create a new query 
			Query query = new Query();

			// return all of the books
			List<Book> books = ops.find(query, Book.class);
			
			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();
			
			// get the search term
			String searchTerm = request.getParameter("searchQuery");
			
			// sort the books by searchTerm
			List<Book> sortedBooks = bookHelper.searchBooks(books, searchTerm);
			
			// if the results are null, instantiate an empty list
			if (sortedBooks == null) {
				sortedBooks = new ArrayList<>();
			}
			
			// make the sorted books available for display
			context.setAttribute(Constants.BOOKS, sortedBooks);
			context.setAttribute("searchQuery", searchTerm);

			// set the url for the search results page
			url = "/search.jsp";
		} else if (action.equals("viewProfile")) {	

			// determine whether the user is logged in, take actions accordingly
			if (session.getAttribute("loggedIn") != null) {
				if ((boolean) session.getAttribute("loggedIn")) {
					
					// set the current user as a session attribute
					User tempUser = (User) context.getAttribute(Constants.USER);
					
					
					// print out the wishlist for testing purposes
					if (tempUser.getWishlist() != null && tempUser.getWishlist().size() > 0) {
						System.out.println("wishlist: ");
						for (int counter = 0; counter < tempUser.getWishlist().size(); counter++) {
							System.out.println(tempUser.getWishlist().get(counter).getName());
						}
					} else {
						System.out.println("wishlist is null or empty");
					}

					
					url = "/profile.jsp";
				} else {
					// clear any messages before going to login page
					String message = ""; 
					session.setAttribute("message", message);
					url = "/login.jsp";
				}
			} else {
				// clear any messages before going to login page
				String message = ""; 
				session.setAttribute("message", message);
				url = "/login.jsp";
			}
		} 
		else if (action.equals("loginAccount")) {
			// check whether an account already exists for the submitted email
			User currentUser = mongoUtil.GetUserByEmail(request.getParameter("email"), ops);
			
			if (currentUser.equals(null)) {
				// if the user is not found, set the message and return 
				String message = "Incorrect email and password combination"; 
				session.setAttribute("message", message);
				url = "/login.jsp";
			} else {
				// if the user is found, check the password
				currentUser.getPassword();
				if (request.getParameter("password").equals(currentUser.getPassword())){
					
					// if they match, set loggedIn to true
					session.setAttribute("loggedIn", true);
					
					// set the current user as a session attribute
					context.setAttribute(Constants.USER, currentUser);				
					
					// clear the message (previously set if a login failed)
					String message = "";
					session.setAttribute("message", message);
					
					// get the new books and redirect to the index page
					url = newBooksIndex(ops, context);
				} else {
					// if the password does not match, set the message and return
					String message = "Incorrect email and password combination"; 
					session.setAttribute("message", message);
					url = "/login.jsp";
				}
			}
		} 
		else if (action.equals("createAccount")) {
			// clear any messages before going to the registration page
			String message = ""; 
			session.setAttribute("message", message);
			
			// set the url for the account creation page
			url = "/register.jsp";
		} 
		else if (action.equals("newAccount")) {
			
			// get the account parameters entered by the user
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String country = request.getParameter("country");
			String zip = request.getParameter("zip");

			// check whether the email has already been registered
			if (mongoUtil.GetUserByEmail(email,ops) == null){
				// user does not exist => create the user
				
				// create an empty payment object, save it to the DB
				Payment payment = new Payment();			
				payment.setEmail(email);
				mongoUtil.SaveOrUpdatePayment(payment, email, ops);
				
				// create an empty cart object, save it to the DB
				Cart cart = new Cart();
				cart.setEmail(email);			
				mongoUtil.SaveOrUpdateCart(cart, email, ops);

				// create an empty list of book objects
				List<Book> wishlist = new ArrayList<Book>();
				
				// create user object from the above info
				boolean isAdmin = false;
				User user = new User(firstname, lastname, email, isAdmin, password, 
						address, city, state, country, zip, payment, cart, wishlist); 
				
				
				// add the user to the database
				User newUser = mongoUtil.SaveOrUpdateUser(user, ops);
				
//				// create an empty list of book name strings
//				List<String> books = new ArrayList<String>();
//				
//				// add the user to the database
//				User newUser = mongoUtil.SaveOrUpdateUser(user, books, ops);

				// set the loggedIn status to true
				session.setAttribute("loggedIn", true);
				
				// set the current user as a session attribute
				context.setAttribute(Constants.USER, newUser);
					
				// clear the message (set if a user uses an existing email)
				String message = "";
				session.setAttribute("message", message);
				
				// redirect to the landing page
				url = newBooksIndex(ops, context);
			} else {
				// user already exists - set message 
				String message = "A user with that email already exists.  Please try again.";
				session.setAttribute("message", message);
				url = "/register.jsp";
			}


		} else if (action.equals("editProfile")) {
			String userID = request.getParameter("userID");

			// get the user's profile data from the database

			// create a userProfile object and fill with pulled data

			// add the userProfile object to the session

			// redirect to edit profile page
			url = "/editProfile.jsp";
		} else if (action.equals("addCart")) {

			// get the current user from the context 
			User sessionUser = (User) context.getAttribute(Constants.USER);
			
			// get the user, the user's cart, and the list of book objects from the cart
			User user = mongoUtil.GetUserByEmail(sessionUser.getEmail(), ops);
			Cart cart = user.getCart();			
			List<Book> books = cart.getBooks();
			
			// get the selected book from the context
			Book addBook = (Book) context.getAttribute(Constants.BOOK);

			// check whether the book is already in the list
			boolean found = false;
			
			if (books != null) {
				for (int counter = 0; counter < books.size(); counter++) {
					if (books.get(counter).getName().equals(addBook.getName())) {
						found = true;
					}
				}
			} else {
				books = new ArrayList<>();
			}
			
			// if the selected book is not in the list, add it
			if (!found) {
				books.add(addBook);
			}
			
			// set the updated list of books in the cart
			cart.setBooks(books); 
			
//			// save the updated cart into the DB - this is done in SaveOrUpdateUser
//			Cart updatedCart = mongoUtil.SaveOrUpdateCart(cart, request.getParameter("email"), ops);
			
			// set the updated cart in the user
			user.setCart(cart);
			
			// save the updated user into the DB
			User updatedUser = mongoUtil.SaveOrUpdateUser(user, ops);
			
			// make the updated user available throughout the session
			context.setAttribute(Constants.USER, updatedUser);
			
			// redirect to cart page
			url = "/cart.jsp";
		} else if (action.equals("removeFromCart")){
			// get the ID of the book to be removed
			String bookID = request.getParameter("removeBookFromCart");
			
			// get the current user from the session 
			User sessionUser = (User) context.getAttribute(Constants.USER);
			
			// get the user, the user's cart, and the list of book objects from the cart
			User user = mongoUtil.GetUserByEmail(sessionUser.getEmail(), ops);
			Cart cart = user.getCart();			
			List<Book> books = cart.getBooks();
			
			// find the book in the list and remove it
			int booksSize = books.size();
			if (books != null) {
				for (int counter = 0; counter < booksSize; counter++)
				{
					if (books.get(counter).getId().equals(bookID)) {
						books.remove(counter);
						counter = booksSize;
					}
				}
			}
			
			// set the updated book list in the cart
			cart.setBooks(books);
			
			// set the updated cart in the user
			user.setCart(cart);
			
			// save the updated user into the DB
			User updatedUser = mongoUtil.SaveOrUpdateUser(user, ops);
			
			// make the updated user available throughout the session
			context.setAttribute(Constants.USER, updatedUser);
			
			// redirect back to the cart
			url = "/cart.jsp";
		} else if (action.equals("cart")) {

			// redirect to the cart page
			url = "/cart.jsp";
		} else if (action.equals("checkout")) {
			
			// activate SSL connection

			// check whether the user is logged in
		
			if (session.getAttribute("loggedIn") != null) {
				if ((boolean) session.getAttribute("loggedIn")) {

					// user is logged in - get data from user object 
					// save payment information?
					
				} else {
					
					// user is not logged in - checkout as guest

				}
			} else {
				
				// logged in status unknown

			}

			// redirect to the checkout page
			url = "/checkout.jsp";
		} else if (action.equals("saveList")) {

			// get the current user from the context 
			User sessionUser = (User) context.getAttribute(Constants.USER);
			
			// get the user, the user's wishlist
			User user = mongoUtil.GetUserByEmail(sessionUser.getEmail(), ops);	
			List<Book> wishlist = user.getWishlist();
			
			// get the selected book from the context
			Book addBook = (Book) context.getAttribute(Constants.BOOK);

			// check whether the book is already in the list
			boolean found = false;
			
			if (wishlist != null) {
				for (int counter = 0; counter < wishlist.size(); counter++) {
					if (wishlist.get(counter).getName().equals(addBook.getName())) {
						found = true;
					}
				}
			} else {
				wishlist = new ArrayList<>();
			}
			
			// if the selected book is not in the list, add it
			if (!found) {
				wishlist.add(addBook);
			}
			
			// set the updated wishlist in the user
			user.setWishlist(wishlist);
			
			// save the updated user into the DB
			User updatedUser = mongoUtil.SaveOrUpdateUser(user, ops);
			
			// make the updated user available throughout the session
			context.setAttribute(Constants.USER, updatedUser);
			
			// redirect to profile page
			url = "/profile.jsp";
		}
		else if (action.equals("removeFromList")){
			// get the ID of the book to be removed
			String bookID = request.getParameter("removeBookFromList");
			
			// get the current user from the session 
			User sessionUser = (User) context.getAttribute(Constants.USER);
			
			// get the user and the wishlist of book objects 
			User user = mongoUtil.GetUserByEmail(sessionUser.getEmail(), ops);			
			List<Book> books = user.getWishlist();
			
			// find the book in the list and remove it
			int booksSize = books.size();
			if (books != null) {
				for (int counter = 0; counter < booksSize; counter++)
				{
					if (books.get(counter).getId().equals(bookID)) {
						books.remove(counter);
						counter = booksSize;
					}
				}
			}
			
			// set the updated wishlist in the user
			user.setWishlist(books);
			
			// save the updated user into the DB
			User updatedUser = mongoUtil.SaveOrUpdateUser(user, ops);
			
			// make the updated user available throughout the session
			context.setAttribute(Constants.USER, updatedUser);
			
			// redirect back to the profile
			url = "/profile.jsp";
		}
		
		
		else if (action.equals("reviewInfo")) {
			// get user and cart info from session object?

			// make sure the info is saved in session object

			// redirect to review information page
			url = "/reviewInfo.jsp";
		} else if (action.equals("orderConfirmation")) {
			// pull user info from session object or database?

			// send the user a confirmation e-mail

			// redirect to confirmation page
			url = "/orderConfirmation.jsp";
		} else if (action.equals("logout")) {
			
			// set the loggedIn attribute to false
			session.setAttribute("loggedIn", false);
			context.setAttribute(Constants.USER, null);
			
			// get the new books, go to index page
			url = newBooksIndex(ops, context);
			
		} else if (action.equals("adminBookInfo")) {
			// confirm admin user

			// get book ID
			String bookID = request.getParameter("bookID");

			// pull book info from database

			// save book info in object and store in session

			// redirect to adminBookInfo
			url = "/adminBookInfo.jsp";
		} else if (action.equals("adminBookDelete")) {
			// get book ID
			String bookID = request.getParameter("bookID");

			// delete the book from the database

			// confirm deletion (db returns 0?)

			// redirect to adminBookDeleteConfirmation page
			url = "/adminBookDeleteConfirmation.jsp";
		} else if (action.equals("adminSaveBookChanges")) {
			// get updated book info from request object
			String bookID = request.getParameter("bookID");
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String genre = request.getParameter("genre");
			String price = request.getParameter("price");
			String description = request.getParameter("description");

			// store the updated data in a book object?

			// send update to the database

			// redirect to update confirmation page
			url = "/adminBookUpdateConfirmation.jsp";
		} else if (action.equals("adminSettings")) {
			// redirect to admin settings page
			url = "/adminSettings.jsp";
		} else if (action.equals("adminManageInventory")) {
			// redirect to admin manage inventory page
			url = "/adminManageInventory.jsp";
		} else if (action.equals("adminAddAdministrator")) {
			// redirect to admin add administrator page
			url = "/adminAddAdministrator.jsp";
		}

		// forward to the view specified in the url
		RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	public void test() {
		// TODO Auto-generated method stub
		MongoTemplate ops = (MongoTemplate) getServletContext().getAttribute(Constants.DATABASE);
		MongoDbUtil mongoUtil = new MongoDbUtil();
		//mongoUtil.PrePopulateBookCollection(ops);
		/*

		private String name;           // name = title

		private String author;

		private String publishedDate;  // mm/dd/yyyy  

		private String genre;

		private double price;

		private String description;

		private String coverImageLink;  // link to the image in webapps/coverImages folde
		*/
		
		 
		
		/*
		 * List<String> books = new ArrayList<String>() { new Book(s) {}, };
		 * 
		 * 
		 * // Books /* List<String> books = new ArrayList<String>();
		 * books.add("The Fastest Man Alive"); books.add("The Speed Force");
		 * 
		 * // Payment Payment payment = new Payment();
		 * payment.setCardNumber("1111-2222-3333-4444"); payment.setCardname("Main");
		 * payment.setCardType(CardType.VISA); payment.setExperiationMonth(06);
		 * payment.setExperiationYear(2025); //payment.setEmail("ballen@jla.com");
		 * payment.setEmail("bwayne@jla.com");
		 * 
		 * // User User user = new User(); us\er.setFirstname("Barry");
		 * user.setLastname("Allen");
		 * user.setEmail("ballen@jla.com"); user.setAdmin(false);
		 * user.setPayment(payment); //mongoUtil.SaveOrUpdateUser(user, books, ops);
		 * 
		 * 
		 * // User User user2 = new User(); user2.setFirstname("Bruce");
		 * user2.setLastname("Wayne"); 
		 * user2.setEmail("bwayne@jla.com"); user2.setAdmin(false);
		 * //mongoUtil.SaveOrUpdateUser(user2, books, ops);
		 * 
		 * mongoUtil.SaveOrUpdatePayment(payment, user2.getEmail(), ops);
		 */
	}

	
	private String newBooksIndex(MongoTemplate ops, ServletContext context) {
		// create a new query 
		Query query = new Query();

		// return all of the books
		List<Book> books = ops.find(query, Book.class);
		
		// create a BookHelper object
		BookHelper bookHelper = new BookHelper();
		
		// sort the books by date, return the 12 newest that are not magazines
		List<Book> sortedBooks = bookHelper.newestBooks(books);

		// make the sorted books available for display
		context.setAttribute(Constants.BOOKS, sortedBooks);  

		// redirect to the landing page
		String url = "/index.jsp";	
		
		// return the url string
		return url;
	}
}
