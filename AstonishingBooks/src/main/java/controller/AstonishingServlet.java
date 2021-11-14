package controller;

// default imports
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// additional imports

//import javax.mail.MessagingException;   // need to add mail jar
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class AstonishingServlet
 */
@WebServlet("/AstonishingServlet")
public class AstonishingServlet extends HttpServlet 
{
	
	// anyone know what this line does?
	private static final long serialVersionUID = 1L;
       

	// all that doGet does is call doPost
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    doPost(request, response);
	}

	
	// doPost processes requests and redirects based on the submitted action and other inputs 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	    // declare a variable to hold the forwarding url
	    String url = "/index.jsp";
	    
	    // extract values from the request object
	    String action = request.getParameter("action");
	    
	    // pull books from the database and redirect to the landing page
	    if (action.equals("landingPage"))
	    {
	    	// pull some books from the database (based on date or rating)
	    	
	    	// create a booksForDisplay object and add the pulled books
	    	
	    	// add the booksForDisplay object to the session object
	    	
	    	// set the url for the landing page
	    	url = "/index.jsp";
	    }

	    else if (action.equals("showBookInfo"))
	    {
	    	// get the ID of the book to display
	    	String bookID = request.getParameter("bookID");
	    	
	    	// pull the book info from the database
	    	
	    	// create a bookInfo object and add the pulled info
	    	
	    	// add the bookInfo object to the session object
	    	
	    	// set the url for the book info page
	    	url = "/bookInfo.jsp";	    	
	    }
	    else if (action.equals("bookSearch"))
	    {
	    	// get the search term
	    	String searchTerm = request.getParameter("searchTerm");
	    	
	    	// search the database using the searchTerm
	    	
	    	// count the number of results returned (e.g., results.length())
	    	
	    	// create a searchResult object
	    	
	    	// add the results and results count to the searchResult object
	    	
	    	// add the searchResult object to the session object
	    	
	    	// set the url for the search results page	    	
	    	url = "/searchResults.jsp";	    	
	    }
	    else if (action.equals("signIn"))
	    {
	    	// set the url for the sign-in page
	    	url = "/signIn.jsp";
	    }
	    else if (action.equals("createAccount"))
	    {
	    	// set the url for the account creation page
	    	url = "/createAccount.jsp";
	    }
	    else if (action.equals("newAccount"))
	    {
	    	// get the account parameters entered by the user
	    	String firstName = request.getParameter("firstName");
	    	String lastName = request.getParameter("lastName");
	    	String email = request.getParameter("email");
	    	String password = request.getParameter("password");
	    	String streetAddress = request.getParameter("streetAddress");
	    	String city = request.getParameter("city");
	    	String state = request.getParameter("state");
	    	String country = request.getParameter("country");
	    	String zipCode = request.getParameter("zipCode");
	    	
	    	// add the user to the database
	    	
	    	// redirect to the new account confirmation page
	    	url = "/newAccountConfirmation.jsp";
	    }
	    else if (action.equals("save-list"))
	    {
	    	String userID = request.getParameter("userID");
	    	
	    	// get the user's wish list from the database
	    	
	    	// add the selected book to the wish list
	    	
	    	// add the userProfile object to the session
	    	
	    	// redirect to show profile page
	    	url = "/showProfile.jsp";
	    }
	    else if (action.equals("showProfile"))
	    {
	    	String userID = request.getParameter("userID");
	    	
	    	// get the user's profile data from the database
	    	
	    	// create a userProfile object and fill with pulled data
	    	
	    	// add the userProfile object to the session
	    	
	    	// redirect to show profile page
	    	url = "/showProfile.jsp";
	    }
	    else if (action.equals("editProfile"))
	    {
	    	String userID = request.getParameter("userID");
	    	
	    	// get the user's profile data from the database
	    	
	    	// create a userProfile object and fill with pulled data
	    	
	    	// add the userProfile object to the session
	    	
	    	// redirect to edit profile page
	    	url = "/editProfile.jsp";
	    }
	    else if (action.equals("add-cart"))
	    {
	    	// get cart contents from session object? or from database?
	    	
	    	// add the selected book to the cart
	    	
	    	// save the cart contents in an object and save to the session
	    	
	    	// redirect to cart page
	    	url = "/cart.jsp";
	    }
	    else if (action.equals("cart"))
	    {
	    	// get cart contents from session object? or from database?
	    	
	    	// save the cart contents in an object and save to the session
	    	
	    	// redirect to cart page
	    	url = "/cart.jsp";
	    }
	    else if (action.equals("checkout"))
	    {
	    	// activate SSL connection
	    	
	    	boolean userIsLoggedIn = false;
	    	
	    	// check whether the user is logged in
	    	
	    	if (userIsLoggedIn)
	    	{
	    		// get stored user info from database
	    		
	    		// put pulled info into object and save to the session
	    	}
	    	
	    	// redirect to the checkout page
	    	url = "/checkout.jsp";	    	
	    }
	    else if (action.equals("reviewInfo"))
	    {
	    	// get user and cart info from session object?
	    	
	    	// make sure the info is saved in session object
	    	
	    	//redirect to review information page
	    	url = "/reviewInfo.jsp";
	    }
	    else if (action.equals("orderConfirmation"))
	    {
	    	// pull user info from session object or database?
	    	
	    	// send the user a confirmation e-mail
	    	
	    	// redirect to confirmation page
	    	url = "/orderConfirmation.jsp";
	    }
	    else if (action.equals("adminBookInfo"))
	    {
	    	// confirm admin user
	    	
	    	// get book ID
	    	String bookID = request.getParameter("bookID");
	    	
	    	// pull book info from database
	    	
	    	// save book info in object and store in session
	    	
	    	// redirect to adminBookInfo 
	    	url = "/adminBookInfo.jsp";
	    }
	    else if (action.equals("adminBookDelete"))
	    {
	    	// get book ID
	    	String bookID = request.getParameter("bookID");
	    	
	    	// delete the book from the database
	    	
	    	// confirm deletion (db returns 0?)
	    	
	    	// redirect to adminBookDeleteConfirmation page
	    	url = "/adminBookDeleteConfirmation.jsp";	    	
	    }
	    else if (action.equals("adminSaveBookChanges"))
	    {
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
	    }
	    else if (action.equals("adminSettings"))
	    {
	    	// redirect to admin settings page
	    	url = "/adminSettings.jsp";
	    }
	    else if (action.equals("adminManageInventory"))
	    {
	    	// redirect to admin manage inventory page
	    	url = "/adminManageInventory.jsp";
	    }
	    else if (action.equals("adminAddAdministrator"))
	    {
	    	// redirect to admin add administrator page
	    	url = "/adminAddAdministrator.jsp";
	    }
	    
	    
	    

	    // forward to the view specified in the url
	    RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	    
	    
	}

}
