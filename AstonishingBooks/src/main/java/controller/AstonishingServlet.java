package controller;

import java.io.File;
// default imports
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// additional imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Query;

import mongobusiness.Book;
import mongobusiness.CardType;
import mongobusiness.Cart;
import mongobusiness.Payment;
import mongobusiness.User;
import utils.Constants;
import utils.DateUtil;
import utils.MongoDbUtil;

/**
 * Servlet implementation class AstonishingServlet
 */
@WebServlet("/AstonishingServlet")
public class AstonishingServlet extends HttpServlet {

	// set the serialVersionUID
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

		// extract the action to perform from the request object
		String action = request.getParameter("action");
		
//		if (action == null) {
//			action = "showBookInfo";
//		}

		// get the session object
		HttpSession session = request.getSession();

		// get the context object
		ServletContext context = getServletContext();

		// create a mongo template
		MongoTemplate ops = (MongoTemplate) context.getAttribute(Constants.DATABASE);

		// create a mongo utility object
		MongoDbUtil mongoUtil = new MongoDbUtil();
		

//		context.setAttribute(Constants.FILEUPLOADPATH, "C:\\Users\\Puji\\Documents\\Masters\\605-682-WebAppDevJava\\astonishing-books-proj\\AstonishingBooks\\src\\main\\webapp\\coverImages\\");
		context.setAttribute(Constants.FILEUPLOADPATH, "C:\\Users\\troyt\\git\\EN.605.682.81.FA21\\AstonishingBooks\\src\\main\\webapp\\coverImages\\");
		context.setAttribute(Constants.IMAGEPATH, "./coverImages/");

		
		String servername = request.getServerName();
		if (servername.contains("dev8")) {
			
			context.setAttribute(Constants.FILEUPLOADPATH, "/var/local/pkg/apache-tomcat-8.0.18/webapps/images/");
			context.setAttribute(Constants.IMAGEPATH, "/images/");
		} 
		/*
		else {
			context.setAttribute(Constants.FILEUPLOADPATH, "C:\\Users\\GMcDo\\git\\EN.605.682.81.FA21\\AstonishingBooks\\src\\main\\webapp\\coverImages\\");
			context.setAttribute(Constants.IMAGEPATH, "./coverImages/");
		}
*/
		if (action.equals("goToHome")) {

			//mongoUtil.MapImagsToBooks(ops);
			// pull the new books and redirect to the index page
			url = newBooksIndex(ops, context);
		} else if (action.equals("goToFiction") || action.equals("goToNonFiction") || action.equals("goToMagazine")
				|| action.equals("goToReference")) {

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

			// sort the books by genre string
			List<Book> sortedBooks = bookHelper.searchBooks(books, genreString, ops);

			// make the sorted books available for display
			context.setAttribute(Constants.BOOKS, sortedBooks);
		} else if (action.equals("showBookInfo")) {
			// get the ID of the book to display
			String bookID = request.getParameter("bookId");
			
			//if (bookID != null && !bookID.isEmpty()) {

				// create a new query
				Query query = new Query();
	
				// return all of the books
				List<Book> books = ops.find(query, Book.class);
	
				// create a BookHelper object
				BookHelper bookHelper = new BookHelper();
	
				// get the selected book
				Book foundBook = bookHelper.bookById(books, bookID, ops);
	
				// make the selected book available for display
				session.setAttribute(Constants.BOOK, foundBook);
		//	}

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
			List<Book> sortedBooks = bookHelper.searchBooks(books, searchTerm, ops);

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
					// user is logged in, go to their profile
					url = "/profile.jsp";
				} else {
					// clear any messages before going to login page
					String message = "";
					session.setAttribute("message", message);
					// user is not logged in, go to login page
					url = "/login.jsp";
				}
			} else {
				// clear any messages before going to login page
				String message = "";
				session.setAttribute("message", message);
				// login status is null, go to login page
				url = "/login.jsp";
			}
		} else if (action.equals("loginAccount")) {
			// check whether an account already exists for the submitted email
			User currentUser = mongoUtil.GetUserByEmail(request.getParameter("email"), ops);

			if (currentUser == null) {
				// if the user is not found, set the message and return
				String message = "Incorrect email and password combination";
				session.setAttribute("message", message);
				url = "/login.jsp";
			} else {
				// if the user is found, check the password
				currentUser.getPassword();
				if (request.getParameter("password").equals(currentUser.getPassword())) {

					// if they match, set loggedIn to true
					session.setAttribute("loggedIn", true);

					// transfer books from the guest cart to the user's cart
					// get the guest user
					User guestUser = (User) context.getAttribute(Constants.USER);

					// get the guest user's cart
					Cart guestCart = guestUser.getCart();

					// get the list of books from the cart
					List<Book> guestBooks = guestCart.getBooks();

					// get the current user's cart
					Cart currentCart = currentUser.getCart();

					// get the current user's book list
					List<Book> currentBooks = currentCart.getBooks();

					// if any books are in the guest user's cart, transfer them to the current
					// user's cart
					if (guestBooks != null && guestBooks.size() > 0) {

						// loop through all of the books in the guest's cart
						for (int guestCounter = 0; guestCounter < guestBooks.size(); guestCounter++) {
							boolean isFound = false; // boolean used when checking for duplicates

							if (currentBooks != null) {
								// loop through the books in the current user's cart
								for (int currentCounter = 0; currentCounter < 0; currentCounter++) {
									if (guestBooks.get(guestCounter).getId()
											.equals(currentBooks.get(currentCounter).getId())) {
										isFound = true; // the book is already in user's cart
									}
								}
							}
							// if the book is not found in the user's cart, transfer it over
							if (!isFound && currentBooks != null) {
								currentBooks.add(guestBooks.get(guestCounter));
							}
						}
					}

					// set the updated list of books in the current user's cart
					currentCart.setBooks(currentBooks);

					// update the cart in the DB
					Cart updatedCart = mongoUtil.SaveOrUpdateCart(currentCart, currentUser.getEmail(), ops);

					// set the updated cart in the current user
					currentUser.setCart(updatedCart);

					// update the current user in the DB
					User updatedUser = mongoUtil.SaveOrUpdateUser(currentUser, ops);

					// set the current user as a session attribute
					context.setAttribute(Constants.USER, updatedUser);

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
		} else if (action.equals("createAccount")) {
			// clear any messages before going to the registration page
			String message = "";
			session.setAttribute("message", message);

			// set the url for the account creation page
			url = "/register.jsp";
		} else if (action.equals("newAccount")) {

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
			if (mongoUtil.GetUserByEmail(email, ops) == null) {

				// user does not exist => create the user

				// create an empty payment object, save it to the DB
				Payment payment = new Payment();
				payment.setEmail(email);
				mongoUtil.SaveOrUpdatePayment(payment, email, ops);

				// create an empty cart object, save it to the DB
				Cart newCart = new Cart();
				newCart.setEmail(email);
				mongoUtil.SaveOrUpdateCart(newCart, email, ops);
				
				// transfer books from the guest cart to the new user's cart
				// get the guest user
				User guestUser = (User) context.getAttribute(Constants.USER);

				// get the guest user's cart
				Cart guestCart = guestUser.getCart();

				// get the list of books from the cart
				List<Book> guestBooks = guestCart.getBooks();

				// set the new user's cart to hold books from guest
				newCart.setBooks(guestBooks);

				// update the cart in the DB
				Cart cart = mongoUtil.SaveOrUpdateCart(newCart, email, ops);	

				// create an empty list of book objects
				List<Book> wishlist = new ArrayList<Book>();

				// create user object from the above info
				boolean isAdmin = false;
				User user = new User(firstname, lastname, email, isAdmin, password, address, city, state, country, zip,
						payment, cart, wishlist);

				// add the user to the database
				User newUser = mongoUtil.SaveOrUpdateUser(user, ops);

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
		} else if (action.equals("addCart")) {
			// add the selected book to the user's cart

			// get the current user from the context
			User sessionUser = (User) context.getAttribute(Constants.USER);

			// get the user, the user's cart, and the list of book objects from the cart
			User user = mongoUtil.GetUserByEmail(sessionUser.getEmail(), ops);
			Cart cart = user.getCart();
			List<Book> books = cart.getBooks();

			// get the selected book from the context
			Book addBookToCart = (Book) session.getAttribute(Constants.BOOK);

			// check whether the book is already in the list
			boolean found = false;

			if (books != null) {
				for (int counter = 0; counter < books.size(); counter++) {
					if (books.get(counter).getName().equals(addBookToCart.getName())) {
						found = true;
					}
				}
			} else {
				books = new ArrayList<>();
			}

			// if the selected book is not in the list, add it
			if (!found) {
				books.add(addBookToCart);
			}

			// set the updated list of books in the cart
			cart.setBooks(books);

			// set the updated cart in the user
			user.setCart(cart);

			// save the updated user into the DB
			User updatedUser = mongoUtil.SaveOrUpdateUser(user, ops);

			// make the updated user available throughout the session
			context.setAttribute(Constants.USER, updatedUser);

			// redirect to cart page
			url = "/cart.jsp";
		} else if (action.equals("removeFromCart")) {
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
				for (int counter = 0; counter < booksSize; counter++) {
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

			// redirect to the checkout page
			url = "/goSSLtoCheckout.jsp";
		} else if (action.equals("saveList")) {

			// get the current user from the context
			User sessionUser = (User) context.getAttribute(Constants.USER);

			// get the user, the user's wishlist
			User user = mongoUtil.GetUserByEmail(sessionUser.getEmail(), ops);
			List<Book> wishlist = user.getWishlist();

			// get the selected book from the context
			Book addBookToWishlist = (Book) session.getAttribute(Constants.BOOK);

			// check whether the book is already in the list
			boolean found = false;

			if (wishlist != null) {
				for (int counter = 0; counter < wishlist.size(); counter++) {
					if (wishlist.get(counter).getName().equals(addBookToWishlist.getName())) {
						found = true;
					}
				}
			} else {
				wishlist = new ArrayList<>();
			}

			// if the selected book is not in the list, add it
			if (!found) {
				wishlist.add(addBookToWishlist);
			}

			// set the updated wishlist in the user
			user.setWishlist(wishlist);

			// save the updated user into the DB
			User updatedUser = mongoUtil.SaveOrUpdateUser(user, ops);

			// make the updated user available throughout the session
			context.setAttribute(Constants.USER, updatedUser);

			// redirect to profile page
			url = "/profile.jsp";
		} else if (action.equals("removeFromList")) {
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
				for (int counter = 0; counter < booksSize; counter++) {
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

		// review order
		else if (action.equals("reviewInfo")) {
			
			// get the user's profile info
			String checkoutFirstname = request.getParameter("firstname");
			String checkoutLastname = request.getParameter("lastname");
			String checkoutEmail = request.getParameter("email");
			String checkoutAddress = request.getParameter("address");
			String checkoutCity = request.getParameter("city");
			String checkoutState = request.getParameter("state");
			String checkoutCountry = request.getParameter("country");
			String checkoutZip = request.getParameter("zip");

			// get the user's credit card info
			String checkoutCardTypeString = request.getParameter("creditCardType");
			String checkoutCardName = (String) request.getParameter("cardName");
			String checkoutCardNumber = (String) request.getParameter("cardNumber");
			String checkoutExperiationmonthString = request.getParameter("expireMonth");
			String checkoutExperiationyearString = request.getParameter("expireYear");

			// convert strings to appropriate values
			int checkoutExperiationmonth = Integer.parseInt(checkoutExperiationmonthString);
			int checkoutExperiationyear = Integer.parseInt(checkoutExperiationyearString);
			CardType checkoutCardType = CardType.valueOf(checkoutCardTypeString);

			// get the user's payment
			Payment checkoutPayment = new Payment();
			
			// set the payment values
			checkoutPayment.setCardType(checkoutCardType);
			checkoutPayment.setCardname(checkoutCardName);
			checkoutPayment.setCardNumber(checkoutCardNumber);
			checkoutPayment.setExperiationMonth(checkoutExperiationmonth);
			checkoutPayment.setExperiationYear(checkoutExperiationyear);

			// get the user from context
			User currentUser = (User) context.getAttribute(Constants.USER);

			// get the current user's cart
			Cart tempCart = currentUser.getCart();
			
			// create a checkout cart
			Cart checkoutCart = new Cart();
			
			// set the checkout cart email to the checkout email 
			checkoutCart.setEmail(checkoutEmail);
			
			// set the checkout cart books to the tempUser cart books
			checkoutCart.setBooks(tempCart.getBooks());

			// create an empty wishlist
			List<Book> checkoutWishlist = new ArrayList<>();

			// create a checkout user
			User checkoutUser = new User(checkoutFirstname, checkoutLastname, checkoutEmail, 
										false, "", checkoutAddress, checkoutCity, checkoutState, 
										checkoutCountry, checkoutZip, checkoutPayment, checkoutCart, 
										checkoutWishlist);
			
			// save the checkout user to the session object
			session.setAttribute("checkoutUser", checkoutUser);

			// redirect to review information page
			url = "/review_checkout.jsp";

		}  else if (action.equals("orderConfirmation")) {   
		
			// get the checkout user
			User checkoutUser = (User) session.getAttribute("checkoutUser");
		
			// get the current user's cart
			Cart checkoutCart = checkoutUser.getCart();
			
			// get the current user's payment
			Payment checkoutPayment = checkoutUser.getPayment();
			
			// send the user a confirmation e-mail   =================================================================
			
			// send email to user
			String to = checkoutUser.getEmail();
			String from = "HopkinsStudent1234@gmail.com";
			String subject = "Order Confirmation from Astonishing Books";
			
			String bodyStyle = "<body style='background-color:#FFF6EE; font-family: Helvetica, sans-serif; color: #724029;"
					+ "padding: 50px;'>";
		      
			String greeting = "";
			greeting = "<h2>Your order has been confirmed!</h2></br></br>" 
					+ "<h3>Your " + checkoutPayment.getCardType() + " has been charged $" + checkoutCart.getTotal() 
					+ "</h3></br></br><h3>Your order will be sent to:</h3></br>"
					+ "<h3>" + checkoutUser.getFirstname() + " " + checkoutUser.getLastname() + "</h3>"
					+ "<h3>" + checkoutUser.getAddress() + "<h3>"
					+ "<h3>" + checkoutUser.getCity() + ", " + checkoutUser.getState() + " " + checkoutUser.getZip() + "</h3></br></br>";

			String tableHeader = "<table style='border: 1px solid #724029' >"
					+ "<tr>"
					+ "<th style='border: 1px solid #724029; padding 5px;' >Title</th>"
					+ "<th style='border: 1px solid #724029; padding 5px;' >Price</th>";
			
			String tableBooks = "";
			for (Book book: checkoutCart.getBooks()) {
				tableBooks += "<tr>"
						+"<td style='border: 1px solid #724029; padding: 5px;' >" + book.getName() + "</td>"
						+"<td style='text-align: center; border: 1px solid #724029; padding: 5px;' >$" + book.getPrice() + "</td>";
			}
			
			String closeTags = "</table></body>";
			

			
			String body = bodyStyle + greeting + tableHeader + tableBooks + closeTags;
			
		    boolean isBodyHTML = true;
		      
			try {
			    MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
			} catch (MessagingException e) {
			  String errorMessage
			            = "ERROR: Unable to send email. "
			        + "Check Tomcat logs for details.<br>"
			        + "NOTE: You may need to configure your system "
			        + "as described in chapter 14.<br>"
			        + "ERROR MESSAGE: " + e.getMessage();
			  request.setAttribute("errorMessage", errorMessage);
			  this.log(
			        "Unable to send email. \n"
			        + "Here is the email you tried to send: \n"
			        + "=====================================\n"
			        + "TO: " + checkoutUser.getEmail() + "\n"
			        + "FROM: " + from + "\n"
			        + "SUBJECT: " + subject + "\n"
			        + "\n"
			        + body + "\n\n");
			
			}
			
			// get the current user
			User currentUser = (User) context.getAttribute(Constants.USER);
		
			// get the current user's cart
			Cart currentCart = currentUser.getCart();
			
			// create an empty list of books
			List<Book> emptyList = new ArrayList<>();		

			// now that the order was placed, replace the cart list with an empty list
			currentCart.setBooks(emptyList);

			// update the cart in the user
			currentUser.setCart(currentCart);

			// save the updates in the DB
			User updatedUser = mongoUtil.SaveOrUpdateUser(currentUser, ops);

			// make the updated user available
			context.setAttribute(Constants.USER, updatedUser);

			// redirect to confirmation page
			url = "/order_confirmation.jsp";
		} else if (action.equals("logout")) {

			// set the loggedIn attribute to false
			session.setAttribute("loggedIn", false);
			context.setAttribute(Constants.USER, null);

			// get the guest user
			User guestUser = mongoUtil.GetUserByEmail("guest@guest.com", ops);

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

			// get the new books, go to index page
			url = newBooksIndex(ops, context);

		}

		// ======================================================================================================================
		// admin actions below
		// ======================================================================================================================
		// goes to admin main page
		else if (action.equals("adminPage")) {
			url = "/admin_view.jsp";
		}
		// this takes the admin user to the manage inventory page
		else if (action.equals("inventory")) {
			// create a new query
			Query query = new Query();

			// return all of the books
			List<Book> books = ops.find(query, Book.class);

			// make the books available for display
			context.setAttribute(Constants.BOOKS, books);

			// set the url
			url = "/admin_manage_inventory.jsp";

		} else if (action.equals("manageUsers")) {

			List<User> users = new MongoDbUtil().findAllUsers(ops);

			// make the users available for display
			session.setAttribute("users", users);

			url = "/admin_manage_users.jsp";
		}

		// this takes the admin user to the admin book info page to edit a selected book
		else if (action.equals("editBook")) {
			// get the ID of the book to display
			String bookID = request.getParameter("bookId");

			// create a new query
			Query query = new Query();

			// return all of the books
			List<Book> books = ops.find(query, Book.class);

			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();

			// get the selected book
			Book foundBook = bookHelper.bookById(books, bookID, ops);

			// make the selected book available for display
			session.setAttribute(Constants.BOOK, foundBook);
			// request.setAttribute("book", foundBook);

			// redirect to adminBookInfo
			url = "/admin_book_info.jsp";
		}

		// this deletes a selected book from the DB
		else if (action.equals("deleteBook")) {
			// get the ID of the book to be removed
			String bookID = request.getParameter("bookId");
			
			System.out.println("Book ID: " + bookID);

			// create a new query
			Query query = new Query();

			// return all of the books
			List<Book> books = ops.find(query, Book.class);

			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();

			// get the selected book
			Book foundBook = bookHelper.bookById(books, bookID, ops);

			// if the book is found in the DB, delete it
			if (foundBook != null) {
				mongoUtil.AddOrDeleteBook(Constants.DELETE, foundBook, ops);
			}
			
			// return all of the books after the delete
			books = ops.find(query, Book.class);
			
			// set the books for display
			context.setAttribute(Constants.BOOKS, books);  

			// return to admin manage inventory page
			url = "/admin_manage_inventory.jsp";
		}

		// this adds a new book to the DB
		else if (action.equals("addBook")) {

			// go to the admin add book page to fill in the fields
			url = "/admin_add_book.jsp";
		}
		
		else if (action.equals("createBook")) {
			
			// get the book parameters
			String name = request.getParameter("name");
			String author = request.getParameter("author");
			String publishedDateString = request.getParameter("publishedDate");
			String genre = request.getParameter("genre");
			String priceString = request.getParameter("price");
			String description = request.getParameter("description");
			String coverImageLink = request.getParameter("coverImageLink");
			
			
//			// check whether the image exists
//			String checkLink = "C:\\Users\\troyt\\git\\EN.605.682.81.FA21\\AstonishingBooks\\src\\main\\webapp" + coverImageLink + ".jpg";
//			File tempFile = new File(checkLink);
//			boolean exists = tempFile.exists();
//			System.out.println("cover image exists: " + exists);
			
			// convert date string to date object
			Date publishedDate = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");		
			
			try {
				publishedDate = formatter.parse(publishedDateString);
			} catch (Exception e) {
				System.out.println("An error has occured");
				System.out.println(e.getMessage());
			}
			
			// convert the price string to a double
			double price = Double.parseDouble(priceString);
			
			// create a book object
			Book newBook = new Book();
			String appPath = (String)context.getAttribute(Constants.FILEUPLOADPATH);
			// add the parameters to the book
			newBook.setName(name);
			newBook.setAuthor(author);
			newBook.setPublishedDate(publishedDate);
			newBook.setGenre(genre);
			newBook.setPrice(price);
			newBook.setDescription(description);
			newBook.setCoverImageLink((String)context.getAttribute(Constants.IMAGEPATH)+coverImageLink);
			
			// add the book to the DB
			mongoUtil.AddOrDeleteBook(Constants.ADD, newBook, ops);
			
			// get the book back from the DB 
			Book book = mongoUtil.FindBookByname(newBook.getName(), ops);
			
			// see what got saved
			System.out.println("ID: " + book.getId());
			System.out.println("name: " + book.getName());
			System.out.println("author: " + book.getAuthor());
			System.out.println("publishedDate: " + book.getPublishedDate());
			System.out.println("genre: " + book.getGenre());
			System.out.println("price: " + book.getPrice());
			System.out.println("description: " + book.getDescription());
			System.out.println("coverImageLink: " + book.getCoverImageLink());
			
			// save the book for display
			session.setAttribute(Constants.BOOK, book);
			//response.setIntHeader("refresh", 1);
			
			// redirect to the book info page
			url = "/book_info.jsp";
		}

		// this part needs to save info from the admin_book_info page into the DB and
		// return to the inventory page ==============
		else if (action.equals("saveBook")) {
			// Retrieve book from session
			Book currentBook = (Book) session.getAttribute(Constants.BOOK);
			
			System.out.println("currentBook ID: " + currentBook.getId());
			System.out.println("currentBook name: " + currentBook.getName());
			System.out.println("book to save: " + currentBook.getName());
			
			// return all of the books 
			List<Book> books = (ArrayList<Book>) context.getAttribute(Constants.BOOKS);
			
			Book bookInContext = books.stream().filter(b -> currentBook.getId().equals(b.getId())).findFirst()
					.orElse(null);
			
			// Find index of book to be updated
			int bookIndex = books.indexOf(bookInContext);
			
			System.out.println("index of book to save: " + bookIndex);

			// get updated book info from request object
			String name = request.getParameter("name");
			String author = request.getParameter("author");
			String publishedDateString = request.getParameter("publishedDate");
			String genre = request.getParameter("genre");
			String price = request.getParameter("price");
			String description = request.getParameter("description");
			String coverImageLink = request.getParameter("coverImageLink");
						
			// convert date string to date object
			Date publishedDate = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");		
			
			try {
				publishedDate = formatter.parse(publishedDateString);
			} catch (Exception e) {
				System.out.println("An error has occured");
				System.out.println(e.getMessage());
			}

			// store the updated data in the book object
			currentBook.setName(name);
			currentBook.setAuthor(author);
			currentBook.setPublishedDate(publishedDate);
			currentBook.setGenre(genre);
			currentBook.setPrice(Double.parseDouble(price));
			currentBook.setDescription(description);
			
			if (coverImageLink != null && !coverImageLink.isEmpty()) {
				currentBook.setCoverImageLink((String)context.getAttribute(Constants.IMAGEPATH)+coverImageLink);
			}

			System.out.println("Before DB update");
			System.out.println("ID: " + currentBook.getId());
			System.out.println("title: " + currentBook.getName());
			System.out.println("author: " + currentBook.getAuthor());
			System.out.println("genre: " + currentBook.getGenre());
			System.out.println("price: " + currentBook.getPrice());
			System.out.println("description: " + currentBook.getDescription());
			System.out.println("coverImageLink: " + currentBook.getCoverImageLink());

			// Update book in database
			mongoUtil.UpdateBook(currentBook, ops);
			
			// get all of the books from the DB
			Query query = new Query();
			
			// return all of the books after the update
			books = ops.find(query, Book.class);
			
			System.out.println("books after update: " + books.size());
			
			// create a BookHelper object
			BookHelper bookHelper = new BookHelper();
			
			Book afterDBUpdate = bookHelper.bookById(books, currentBook.getId(), ops);
			
			System.out.println("\nAfter DB update");
			System.out.println("ID: " + afterDBUpdate.getId());
			System.out.println("title: " + afterDBUpdate.getName());
			System.out.println("author: " + afterDBUpdate.getAuthor());
			System.out.println("genre: " + afterDBUpdate.getGenre());
			System.out.println("price: " + afterDBUpdate.getPrice());
			System.out.println("description: " + afterDBUpdate.getDescription());
			System.out.println("coverImageLink: " + afterDBUpdate.getCoverImageLink());

			
			// set the books for display
			context.setAttribute(Constants.BOOKS, books);  

			// redirect to the manage inventory page
			url = "/book_info.jsp";
		}

		// need a way to navigate to the manage users page
		// =====================================================================
		// this needs to be updated to save a change in a user's privileges (isAmin
		// updated to true or false) ==================
		else if (action.equals("giveAdminPrivileges")) {

			// get the updated user
			String email = request.getParameter("email");
			String[] selected = request.getParameterValues("isAdmin");
			boolean isAdmin = selected[0].equals("true") ? false : true;

			// Retrieve list of users in session
			List<User> users = (ArrayList<User>) session.getAttribute("users");

			// Find updated user
			User user = users.stream().filter(u -> email.equals(u.getEmail())).findFirst().orElse(null);

			// Find index of user
			int index = users.indexOf(user);

			// Update permission and update in sessin list
			user.setAdmin(isAdmin);
			users.set(index, user);

			// Update in Database
			mongoUtil.UpdateAdminPermission(email, isAdmin, ops);

			// return to admin manage users page
			url = "/admin_manage_users.jsp";
		}

		// forward to the view specified in the url
		RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	private String convertToCamelCase(String title) {
		
		if (title.length() == 0) return "";
		
		String result = "";
		String [] tArray = title.split(" ");
		result += tArray[0].toLowerCase();
		for (int i = 1; i < tArray.length; i++) {
			
			String temp = "";
			temp += tArray[i].substring(0, 1).toUpperCase();
			temp += tArray[i].substring(1).toLowerCase();
			result += temp;
		}		
		return result;
	}

	private String newBooksIndex(MongoTemplate ops, ServletContext context) {
		// create a new query
		Query query = new Query();

		// return all of the books
		List<Book> books = ops.find(query, Book.class);

		// create a BookHelper object
		BookHelper bookHelper = new BookHelper();

		// sort the books by date, return the 12 newest that are not magazines
		List<Book> sortedBooks = bookHelper.newestBooks(books, ops);

		// make the sorted books available for display
		context.setAttribute(Constants.BOOKS, sortedBooks);

		// set the url
		String url = "/index.jsp";

		// return the url string
		return url;
	}
}
