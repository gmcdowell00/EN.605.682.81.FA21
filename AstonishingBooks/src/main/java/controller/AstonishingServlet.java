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
			url = "/index.jsp";		

		} 
		
//		else if (action.equals("goToFiction")) {
//			// pull 12 fiction books - using search function?
//			System.out.println("Going to fiction");
//			// change to fiction page
//			url = "/fiction_page.jsp";
//		} else if (action.equals("goToNonFiction")) {
//			// pull 12 non-fiction books - using search function?
//			System.out.println("Going to non-fiction");
//			// change to non-fiction page
//			url = "/nonfiction_page.jsp";
//		} else if (action.equals("goToMagazine")) {
//			// pull 12 magazines - using search function?
//			System.out.println("Going to magazine");
//			// change to magazine page
//			url = "/magazine_page.jsp";
//		} else if (action.equals("goToReference")) {
//			// pull 12 reference books - using search function?
//			System.out.println("Going to reference");
//			// change to reference page
//			url = "/reference_page.jsp";
//		} 
		
		else if (action.equals("goToFiction") || action.equals("goToNonFiction") || 
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
			//  String bookID = request.getParameter("bookId");

			// pull the book info from the database

			// create a bookInfo object and add the pulled info

			// add the bookInfo object to the session object

			System.out.println("book info");
			
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
			
			System.out.println("search term: " + searchTerm);

			// sort the books by searchTerm
			List<Book> sortedBooks = bookHelper.searchBooks(books, searchTerm);
			
			// make the sorted books available for display
			context.setAttribute(Constants.BOOKS, sortedBooks);  	
			
			// see what gets found
			for (int counter = 0; counter < sortedBooks.size(); counter++) {
				System.out.println(sortedBooks.get(counter).getName());
			}
			
			url = "/search.jsp";
		} else if (action.equals("viewProfile")) {		
			if (session.getAttribute("loggedIn") != null) {
				if ((boolean) session.getAttribute("loggedIn")) {
					url = "/profile.jsp";
				} else {
					url = "/login.jsp";
				}
			} else {
				url = "/login.jsp";
			}
		} 
		else if (action.equals("loginAccount")) {
			// check the username and password
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
					
					// clear the message (previously set if a login failed)
					String message = "";
					session.setAttribute("message", message);
					
					// redirect to the landing page
					url ="/index.jsp";
				} else {
					// if the password does not match, set the message and return
					String message = "Incorrect email and password combination"; 
					session.setAttribute("message", message);
					url = "/login.jsp";
				}
			}
		} 
		else if (action.equals("createAccount")) {
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

//			MongoTemplate ops = (MongoTemplate) getServletContext().getAttribute(Constants.DATABASE);
//			MongoDbUtil mongoUtil = new MongoDbUtil();

			if (mongoUtil.GetUserByEmail(email,ops) == null){
				// user does not exist => create the user
				
				// create empty payment object, save it to the DB
				Payment payment = new Payment();			
				payment.setEmail(email);
				mongoUtil.SaveOrUpdatePayment(payment, email, ops);
				
				// create empty cart object, save it to the DB
				Cart cart = new Cart();
				cart.setEmail(email);			
				mongoUtil.SaveOrUpdateCart(cart, email, ops);

				// create empty list of books
				List<String> books = new ArrayList<String>();
				
				// create user object from the above info
				boolean isAdmin = false;
				User user = new User(firstname, lastname, firstname, email, isAdmin, password, 
						address, city, state, country, zip, payment, cart); 
				
				// add the user to the database
				User newUser = mongoUtil.SaveOrUpdateUser(user, books, ops);

				// set the loggedIn status to true
				session.setAttribute("loggedIn", true);
				
				// clear the message (set if a user uses an existing email)
				String message = "";
				session.setAttribute("message", message);
				
				// redirect to the landing page
				url = "/index.jsp";
			} else {
				String message = "A user with that email already exists.  Please try again.";
				session.setAttribute("message", message);
				url = "/register.jsp";
			}


		} else if (action.equals("saveList")) {
			// String userID = request.getParameter("userID");

			// get the user's wish list from the database

			// add the selected book to the wish list

			// add the userProfile object to the session

			// redirect to show profile page
			url = "/profile.jsp";
		} 
//		else if (action.equals("viewProfile")) {
//			// String userID = request.getParameter("userID");
//
//			// get the user's profile data from the database
//
//			// create a userProfile object and fill with pulled data
//
//			// add the userProfile object to the session
//
//			// redirect to show profile page
//			url = "/showProfile.jsp";
//		} 
		else if (action.equals("editProfile")) {
			String userID = request.getParameter("userID");

			// get the user's profile data from the database

			// create a userProfile object and fill with pulled data

			// add the userProfile object to the session

			// redirect to edit profile page
			url = "/editProfile.jsp";
		} else if (action.equals("addCart")) {

//			MongoTemplate ops = (MongoTemplate) getServletContext().getAttribute(Constants.DATABASE);
//			MongoDbUtil mongoUtil = new MongoDbUtil();
			
			User user = mongoUtil.GetUserByEmail(request.getParameter("email"), ops);
			Cart cart = user.getCart();
			
			List<Book> books = cart.getBooks();

			//=================================
			// check the list of books to see if the new book is on the list
			// if so, no change necessary
			// if not, add the book to the list, save to the cart
			//=================================

			cart.setBooks(books); 
			
			Cart updatedCart = mongoUtil.SaveOrUpdateCart(cart, request.getParameter("email"), ops);
			
			// redirect to cart page
			url = "/cart.jsp";
		} else if (action.equals("cart")) {
			// get cart contents from session object? or from database?

			// save the cart contents in an object and save to the session

			// redirect to cart page
			url = "/cart.jsp";
		} else if (action.equals("checkout")) {
			// activate SSL connection

			boolean userIsLoggedIn = false;

			// check whether the user is logged in

			if (userIsLoggedIn) {
				// get stored user info from database

				// put pulled info into object and save to the session
			}

			// redirect to the checkout page
			url = "/checkout.jsp";
		} else if (action.equals("reviewInfo")) {
			// get user and cart info from session object?

			// make sure the info is saved in session object

			// redirect to review information page
			url = "/reviewInfo.jsp";
		} else if (action.equals("orderConfirmation")) {
			// pull user info from session object or database?

			// send the user a confirmation e-mail

			// redirect to confirmation page
			url = "/orderConfirmation.jsp";
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
		 * user.setLastname("Allen"); user.setUsername("ScarlettSpeedster007");
		 * user.setEmail("ballen@jla.com"); user.setAdmin(false);
		 * user.setPayment(payment); //mongoUtil.SaveOrUpdateUser(user, books, ops);
		 * 
		 * 
		 * // User User user2 = new User(); user2.setFirstname("Bruce");
		 * user2.setLastname("Wayne"); user2.setUsername("DarkKnight");
		 * user2.setEmail("bwayne@jla.com"); user2.setAdmin(false);
		 * //mongoUtil.SaveOrUpdateUser(user2, books, ops);
		 * 
		 * mongoUtil.SaveOrUpdatePayment(payment, user2.getEmail(), ops);
		 */
	}

}
