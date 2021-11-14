package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.mongodb.core.MongoTemplate;

import mongobusiness.Book;
import mongobusiness.CardType;
import mongobusiness.Cart;
import mongobusiness.Payment;
import mongobusiness.Test;
import mongobusiness.User;
import utils.MongoDbUtil;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/Test")
public class testservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MongoTemplate ops = (MongoTemplate)getServletContext().getAttribute("Database");  
		MongoDbUtil mongoUtil = new MongoDbUtil();
		
	
		// Books
		/*
		List<String> books = new ArrayList<String>();
		books.add("The Fastest Man Alive");
		books.add("The Speed Force");

		// Payment
		Payment payment = new Payment();
		payment.setCardNumber("1111-2222-3333-4444");
		payment.setCardname("Main");
		payment.setCardType(CardType.VISA);
		payment.setExperiationMonth(06);
		payment.setExperiationYear(2025);
		//payment.setEmail("ballen@jla.com");
		payment.setEmail("bwayne@jla.com");
		
		// User
		User user = new User();
		user.setFirstname("Barry");
		user.setLastname("Allen");
		user.setUsername("ScarlettSpeedster007");
		user.setEmail("ballen@jla.com");
		user.setAdmin(false);
		user.setPayment(payment);
		//mongoUtil.SaveOrUpdateUser(user, books, ops);
		
		
		// User
		User user2 = new User();
		user2.setFirstname("Bruce");
		user2.setLastname("Wayne");
		user2.setUsername("DarkKnight");
		user2.setEmail("bwayne@jla.com");
		user2.setAdmin(false);
		//mongoUtil.SaveOrUpdateUser(user2, books, ops);
		
		mongoUtil.SaveOrUpdatePayment(payment, user2.getEmail(), ops);
		*/
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}


