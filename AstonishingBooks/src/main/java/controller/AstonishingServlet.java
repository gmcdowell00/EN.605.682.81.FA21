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

//import javax.mail.MessagingException;   // need to add mail jar
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Field;

import mongobusiness.Book;
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

		// copy the registrant object from the session
		HttpSession session = request.getSession();

		// pull books from the database and redirect to the landing page
		if (action.equals("home") || action.equals("goToHome")) {
			// Get the initial list of books to display on the index page
			// BookDBWorker BookIO = new BookDBWorker(); // need a DB worker object of some
			// kind?
			// ArrayList<Book> initialBooks = BookIO.getInitialBooks(); // get an ArrayList
			// of book objects

			// add the initialBooks object to the session object
			// session.setAttribute("initialBooks", initialBooks);

			// set the url for the home page
			url = "/home.jsp";
			this.test();
		}

		else if (action.equals("showBookInfo")) {
			// get the ID of the book to display
			// String bookID = request.getParameter("bookID");

			// pull the book info from the database

			// create a bookInfo object and add the pulled info

			// add the bookInfo object to the session object

			// set the url for the book info page
			url = "/book_info.jsp";
		} else if (action.equals("search")) {
			// get the search term
			String searchTerm = request.getParameter("searchQuery");

			// search the database using the searchTerm

			// count the number of results returned (e.g., results.length())

			// create a searchResult object

			// add the results and results count to the searchResult object

			// add the searchResult object to the session object

			// set the url for the search results page
			url = "/search.jsp";
		} else if (action.equals("signIn")) {
			// set the url for the sign-in page
			url = "/signIn.jsp";
		} else if (action.equals("createAccount")) {
			// set the url for the account creation page
			url = "/createAccount.jsp";
		} else if (action.equals("newAccount")) {
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
		} else if (action.equals("saveList")) {
			String userID = request.getParameter("userID");

			// get the user's wish list from the database

			// add the selected book to the wish list

			// add the userProfile object to the session

			// redirect to show profile page
			url = "/showProfile.jsp";
		} else if (action.equals("viewProfile")) {
			// String userID = request.getParameter("userID");

			// get the user's profile data from the database

			// create a userProfile object and fill with pulled data

			// add the userProfile object to the session

			// redirect to show profile page
			url = "/showProfile.jsp";
		} else if (action.equals("editProfile")) {
			String userID = request.getParameter("userID");

			// get the user's profile data from the database

			// create a userProfile object and fill with pulled data

			// add the userProfile object to the session

			// redirect to edit profile page
			url = "/editProfile.jsp";
		} else if (action.equals("addCart")) {
			// get cart contents from session object? or from database?

			// add the selected book to the cart

			// save the cart contents in an object and save to the session

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

		/*

		private String name;           // name = title

		private String author;

		private String publishedDate;  // mm/dd/yyyy  

		private String genre;

		private double price;

		private String description;

		private String coverImageLink;  // link to the image in webapps/coverImages folde
		*/
		
		 List<Book> books = new ArrayList<Book>() 
		 {{
//			add( new Book("The Dark Knight Part 1", "Batman returns from hiatus 30 year hiatus", 50.00));
//			add( new Book("The Dark Knight Part 2", "Batman teams up with The Green Arrow", 50.00));
//			add( new Book("The Dark Knight Part 3", "Batman is Challenged by Superman", 50.00));
//			add( new Book("Power Rangers: Green With Evil Part 1", "The Green Ranger enters the battle", 50.00));
			 
			 
			
			// Fiction
			add( new Book("Cloud Cuckoo Land", "Anthony Doerr", "09/28/2021", "Fiction", 23.99, "/coverImages/cloudCuckooLand", 
					"The heroes of Cloud Cuckoo Land are trying to figure out the world around them: Anna and Omeir, "
					+ "on opposite sides of the formidable city walls during the 1453 siege of Constantinople; "
					+ "teenage idealist Seymour in an attack on a public library in present day Idaho; and Konstance, "
					+ "on an interstellar ship bound for an exoplanet, decades from now."));
						
			add( new Book("The Complete Sherlock Holmes", "Sir Arthur Conan Doyle", "06/14/2012", "Fiction", 34.95, 
					"/coverImages/sherlockHolmes", "The game is afoot! Here are the Sherlock Holmes novels and stories, "
							+ "all in one volume and in their proper chronological order, so you can follow the career "
							+ "of the great detective from beginning to end."));
			
			add( new Book("Needful Things", "Stephen King", "03/20/2018", "Fiction", 18.99, "/coverImages/needfulThings", 
					"The town of Castle Rock, Maine has seen its fair share of oddities over the years, but nothing is as "
					+ "peculiar as the little curio shop that’s just opened for business here. Its mysterious proprietor, "
					+ "Leland Gaunt, seems to have something for everyone out on display at Needful Things... Everyone in "
					+ "town seems willing to make a deal at Needful Things, but the devil is in the details. And no one "
					+ "takes heed of the little sign hanging on the wall: Caveat emptor. In other words, let the buyer beware..."));
			
			add( new Book("The Picture of Dorian Gray", "Oscar Wilde", "10/27/2020", "Fiction", 10.00, "/coverImages/dorianGray", 
					"When handsome young Dorian Gray sees a painter’s stunning portrait of him, he is transfixed by its reflection "
					+ "of his own beauty. He says that he would give his soul if only the painting would suffer the ravages of "
					+ "time and he were to remain forever young. From that point on, Dorian lives a life of hedonistic indulgence, "
					+ "knowing that only the painting will show his moral corruption."));
			
			add( new Book("Dune", "Frank Herbert", "08/26/2003", "Fiction", 9.99, "/coverImages/dune", "Set on the desert planet "
					+ "Arrakis, Dune is the story of the boy Paul Atreides, heir to a noble family tasked with ruling an "
					+ "inhospitable world where the only thing of value is the “spice” melange, a drug capable of extending "
					+ "life and enhancing consciousness. Coveted across the known universe, melange is a prize worth killing for...."));
			
			add( new Book("The Lord of the Rings", "J. R. R. Tolkien", "11/02/2021", "Fiction", 59.99, "/coverImages/LOTR", 
					"In ancient times the Rings of Power were crafted by the Elven-smiths, and Sauron, the Dark Lord, "
					+ "forged the One Ring, filling it with his own power so that he could rule all others. But the One Ring "
					+ "was taken from him, and though he sought it throughout Middle-earth, it remained lost to him. "
					+ "After many ages it fell by chance into the hands of the hobbit Bilbo Baggins.  This new edition is "
					+ "illustrated with J.R.R. Tolkien’s own artwork, created as he wrote the original text."));
			
			add( new Book("Candide", "Voltaire", "06/01/2003", "Fiction", 7.95, "/coverImages/candide", "One of the finest "
					+ "satires ever written, Voltaire’s Candide savagely skewers this very “optimistic” approach to life as "
					+ "a shamefully inadequate response to human suffering. The swift and lively tale follows the absurdly "
					+ "melodramatic adventures of the youthful Candide, who is forced into the army, flogged, shipwrecked, "
					+ "betrayed, robbed, separated from his beloved Cunégonde, and tortured by the Inquisition."));
			
			add( new Book("The Mysterious Rider", "Zane Grey", "09/01/2007", "Fiction", 15.55, "/coverImages/mysteriousRider",
					"In The Mysterious Rider, Bill Bellound's foster daughter Columbine agrees to marry his son Jack out of "
					+ "love for her foster father. Jack is a coward, drunkard, gambler, and thief, and Columbine really loves "
					+ "the cowboy Wilson Moore."));
			
			add( new Book("Chain of Command", "Tom Clancy", "11/16/2021", "Fiction", 20.96, "/coverImages/chainOfCommand", 
					"A shadowy billionaire uses his fortune to further his corrupt ambitions. Along the way, he’s toppled "
					+ "democratically elected governments and exacerbated divisions within stable nations. The competitors "
					+ "he’s destroyed, the people he’s hurt, they’re all just marks on a ledger. Now, he’s ready to implement "
					+ "his most ambitious plan of all. There’s only one force standing in his way—President Jack Ryan."));
			
			add( new Book("Sea of Tranquility", "Emily St. John Mandel", "04/05/2022", "Fiction", 11.99, 
					"/coverImages/seaOfTranquility", "Edwin St. Andrew is eighteen years old when he crosses the Atlantic by "
							+ "steamship, exiled from polite society following an ill-conceived diatribe at a dinner party. "
							+ "He enters the forest, spellbound by the beauty of the Canadian wilderness, and suddenly hears "
							+ "the notes of a violin echoing in an airship terminal—an experience that shocks him to his core."));
			
			add( new Book("ExtraOrdinary", "V. E. Schwab", "11/23/2021", "Fiction", 19.99, "/coverImages/extraOrdinary", 
					"Taking place in the years between VICIOUS and VENGEFUL, ExtraOrdinary follows the tale of a teenage "
					+ "girl named Charlotte Tills who following a fatal bus crash, seemingly dies only to wake up to "
					+ "discover she has become an EO — a person with ExtraOrdinary abilities. In Charlotte’s case, it’s "
					+ "the ability to see people's deaths, but when she looks into her own future, sees her own murder at "
					+ "the hands of the self-proclaimed hero and notorious EO killer Eli Ever, who is currently in prison "
					+ "for the murder of Victor Vale. Refusing to accept her fate, Charlotte sets off to find—and change—her "
					+ "future—before it comes for her."));
			
			add( new Book("A Journey to the Center of the Earth", "Jules Verne", "07/24/2017", "Fiction", 15.00, 
					"/coverImages/journeyCenterEarth", "Accompanied by his nephew Harry and his guide Hans, Professor Von Hardwigg "
							+ "follows directions in a coded message left by sixteenth-century alchemist Arne Saknussemm to the "
							+ "crater of the volcano Sneffels. There they descend into a subterranean world that is home to "
							+ "marvels from the prehistoric past that could never exist in the world above ground."));
			
			
			// Non-Fiction
			add( new Book("The Lyrics: 1956 to the Present", "Paul McCartney", "11/02/2021", "Non-Fiction", 100.00, 
					"/coverImages/theLyrics", "A landmark publishing event featuring two volumes of Paul McCartney's lyrics "
							+ "from his time before The Beatles, with The Beatles, Wings and his solo career — 154 songs in "
							+ "all. Included in the book is McCartney's commentary, never-before-seen photos, handwritten "
							+ "notes, art and ephemera, all of which are part of his personal collection and viewed by the "
							+ "public for the first time."));
			
			add( new Book("Will", "Will Smith", "11/09/2021", "Non-Fiction", 23.99, "/coverImages/will", "Will Smith’s "
					+ "transformation from a fearful child in a tense West Philadelphia home to one of the biggest rap stars "
					+ "of his era and then one of the biggest movie stars in Hollywood history, with a string of box office "
					+ "successes that will likely never be broken, is an epic tale of inner transformation and outer triumph, "
					+ "and Will tells it astonishingly well. But it's only half the story."));
			
			add( new Book("Madhouse at the End of the Earth", "Julian Sancton", "05/04/2021", "Non-Fiction", 25.49, 
					"/coverImages/madhouse", "In August 1897, the young Belgian commandant Adrien de Gerlache set sail for "
							+ "a three-year expedition aboard the good ship Belgica with dreams of glory. His destination "
							+ "was the uncharted end of the earth: the icy continent of Antarctica.  After a series of "
							+ "costly setbacks, the commandant faced two bad options: turn back in defeat and spare his "
							+ "men the devastating Antarctic winter, or recklessly chase fame by sailing deeper into the "
							+ "freezing waters. De Gerlache sailed on, and soon the Belgica was stuck fast in the icy"
							+ " hold of the Bellingshausen Sea. When the sun set on the magnificent polar landscape one "
							+ "last time, the ship’s occupants were condemned to months of endless night. In the darkness, "
							+ "plagued by a mysterious illness and besieged by monotony, they descended into madness."));
			
			add( new Book("The Intelligent Investor", "Benjamin Graham", "02/21/2006", "Non-Fiction", 19.99, 
					"/coverImages/intelligentInvestor", "The greatest investment advisor of the twentieth century, "
							+ "Benjamin Graham, taught and inspired people worldwide. Graham's philosophy "
							+ "of \"value investing\"—which shields investors from substantial error and teaches them "
							+ "to develop long-term strategies—has made The Intelligent Investor the stock market bible "
							+ "ever since its original publication in 1949."));
			
			add( new Book("The Fabric of Civilization", "Virginia Postrel", "12/07/2021", "Non-Fiction", 17.99, 
					"/coverImages/fabricOfCivilization", "In The Fabric of Civilization, Virginia Postrel synthesizes "
							+ "groundbreaking research from archaeology, economics, and science to reveal a surprising "
							+ "history. From Minoans exporting wool colored with precious purple dye to Egypt, to Romans "
							+ "arrayed in costly Chinese silk, the cloth trade paved the crossroads of the ancient world. "
							+ "Textiles funded the Renaissance and the Mughal Empire; they gave us banks and bookkeeping, "
							+ "Michelangelo’s David and the Taj Mahal. The cloth business spread the alphabet and "
							+ "arithmetic, propelled chemical research, and taught people to think in binary code."));
			
			add( new Book("Never Cry Wolf", "Farley Mowat", "08/04/2015", "Non-Fiction", 1.99, "/coverImages/neverCryWolf", 
					"In 1948, Farley Mowat landed in the far north of Manitoba, Canada, a young biologist sent to "
					+ "investigate the region’s dwindling population of caribou. Many people thought that the caribous’ "
					+ "conspicuous decline had been caused by the tundra’s most notorious predator: the wolf. Alone "
					+ "among the howling canine packs, Mowat expected to find the bloodthirsty beasts of popular conception. "
					+ "Instead, over the course of a summer spent observing the powerful animals, Mowat discovered an "
					+ "animal species with a remarkable capacity for loyalty, virtue, and playfulness."));
			
			add( new Book("The Rise and Fall of the Dinosaurs", "Steve Brusatte", "04/30/2019", "Non-Fiction", 16.99, 
					"/coverImages/riseFallDinosaurs", "The dinosaurs. Sixty-six million years ago, the Earth’s most "
							+ "fearsome creatures vanished. Today they remain one of our planet’s great mysteries. "
							+ "Now The Rise and Fall of the Dinosaurs reveals their extraordinary, 200-million-year-long "
							+ "story as never before."));
			
			add( new Book("Humble Pi", "Matt Parker", "01/19/2021", "Non-Fiction", 17.00, "/coverImages/humblePi", 
					"Our whole world is built on math, from the code running a website to the equations enabling the "
					+ "design of skyscrapers and bridges. Most of the time this math works quietly behind the scenes . . . "
					+ "until it doesn’t. All sorts of seemingly innocuous mathematical mistakes can have significant "
					+ "consequences.  Exploring and explaining a litany of glitches, near misses, and mathematical mishaps "
					+ "involving the internet, big data, elections, street signs, lotteries, the Roman Empire, and an "
					+ "Olympic team, Matt Parker uncovers the bizarre ways math trips us up, and what this reveals about "
					+ "its essential place in our world. Getting it wrong has never been more fun."));
			
			add( new Book("No One Wins Alone", "Mark Messier", "10/26/2021", "Non-Fiction", 22.99, "/coverImages/noOneWinsAlone", 
					"Mark Messier is one of the most accomplished athletes in the history of professional sports. He was a "
					+ "fierce competitor with a well-earned reputation as a winner. But few people know his real story, not "
					+ "only of the astonishing journey he took to making NHL history, but of the deep understanding of "
					+ "leadership and respect for the power of teamwork he gained."));
			
			add( new Book("The Baseball 100", "Joe Pasnanski", "09/28/2021", "Non-Fiction", 33.99, "/coverImages/baseball100", 
					"Longer than Moby-Dick and nearly as ambitious,The Baseball 100 is a one-of-a-kind work by award-winning "
					+ "sportswriter and lifelong student of the game Joe Posnanski that tells the story of the sport through "
					+ "the remarkable lives of its 100 greatest players."));
			
			add( new Book("The Storyteller", "Dave Grohl", "10/05/2021", "Non-Fiction", 23.99, "/coverImages/storyteller", 
					"Dave Grohl, the twice-inducted Rock & Roll Hall of Fame member for his work with Nirvana and the Foo "
					+ "Fighters, decided to tell stories about his life and music on social media. From there, the stories "
					+ "on social media began to coalesce into a whole which made a book possible. He has lived through and "
					+ "seen a lot, so these tales run the gamut from the halcyon Nirvana days, up to the present as a "
					+ "Foo Fighter."));
			
			add( new Book("The Code Breaker", "Walter Isaacson", "03/09/2021", "Non-Fiction", 27.99, "/coverImages/codeBreaker", 
					"Jennifer Doudna and her collaborators turned a curiosity of nature into an invention that will transform "
					+ "the human race: an easy-to-use tool that can edit DNA. Known as CRISPR, it opened a brave new world of "
					+ "medical miracles and moral questions.  Should we allow parents, if they can afford it, to enhance the "
					+ "height or muscles or IQ of their kids?"));
			
			
			
			// Magazine
			add( new Book("People", "Meredith Corporation", "11/22/2021", "Magazine", 2.04, "/coverImages/people", "Celebrity gossip"));
			
			add( new Book("National Geographic", "National Geographic", "10/1/2021", "Magazine", 1.67, "/coverImages/natGeo", 
					"Get a glimpse into the exotic and the unexplored with exceptional photography and story-telling."));

			add( new Book("Reader’s Digest", "Reader’s Digest Association Inc", "11/1/2021", "Magazine", 1.50, 
					"/coverImages/readersDigest", "Who can keep up with 8,500 magazines and newspapers, 2,300 TV channels, "
							+ "plus more than 200 million websites? In an era of information overload, Readers Digest offers "
							+ "something unique: the very best advice, information and inspiration from multiple sources, "
							+ "condensed into a consumer-friendly digest."));

			add( new Book("TIME Magazine", "Meredith Corporation", "11/22/2021", "Magazine", 0.58, "/coverImages/timeMagazine", 
					"Offering a rare convergence of incisive reporting, lively writing and world-renowned photography, "
					+ "TIME has been credited with bringing journalism at its best into the fabric of American life."));

			add( new Book("The New Yorker", "Conde Nast", "11/22/2021", "Magazine", 2.13, "/coverImages/newYorker", 
					"The New Yorker is a national weekly magazine that offers a signature mix of reporting and commentary "
					+ "on politics, foreign affairs, business, technology, popular culture, and the arts, along with humor, "
					+ "fiction, poetry, and cartoons."));

			add( new Book("Sports Illustrated", "Maven Coalition Inc", "11/1/2021", "Magazine", 0.76, 
					"/coverImages/sportsIllustrated", "Through emotional storytelling and award-winning photography, "
							+ "Sports Illustrated provides you with complete coverage of all your favorite sports, "
							+ "including the NFL, College Football, Baseball, College Basketball, the NBA and more."));

			add( new Book("Newsweek", "IBT Media", "11/19/2021", "Magazine", 0.46, "/coverImages/newsweek", "Trusted "
					+ "for more than 75 years, Newsweek provides in depth analysis and insight into the most significant "
					+ "issues of the day. With some of the most influential journalists in the business, Newsweek brings "
					+ "you well-reasoned opinions, expert voices, and incisive reporting that drive and shape global "
					+ "conversations every day, every week, every year."));

			add( new Book("Entertainment Weekly", "Meredith Corporation", "12/1/2021", "Magazine", 1.14, 
					"/coverImages/entertainmentWeekly", "Your essential weekly guide to the latest movies, "
							+ "TV, music, books and more."));

			add( new Book("HGTV Magazine", "Hearst", "12/1/2021", "Magazine", 2.00, "/coverImages/hgtv", "In every "
					+ "issue of HGTV Magazine, you'll learn insider secrets from your favorite HGTV stars. Plus, tour "
					+ "the coolest designer homes from across the country, check out fun decorating tips, DIY "
					+ "solutions, home makeovers, and more!"));

			add( new Book("Consumer Reports", "Consumer Reports", "12/1/2021", "Magazine", 2.00, 
					"/coverImages/consumerReports", "Ratings, recommendations, reliability reports, safety and price "
							+ "comparisons from the world's largest consumer testing center. Helps consumers make "
							+ "better choices for everything from cars to cell phone service. The only magazine of "
							+ "its kind: Expert, independent, nonprofit. 100% unbiased. Consumer Reports accepts no "
							+ "outside advertising."));

			add( new Book("Vanity Fair", "Conde Nast", "11/1/2021", "Magazine", 2.50, "/coverImages/vanityFair", 
					"From entertainment to world affairs, business to style, design to society, Vanity Fair is a "
					+ "cultural catalyst, inspiring and driving the national conversation."));

			add( new Book("Discover", "Kalmbach Publishing Co", "11/1/2021", "Magazine", 3.12, "/coverImages/discover", 
					"Discover magazine takes you on the most exciting adventure of our times: The scientific quest to "
					+ "uncover our origins, understand the world around us, and build a better future. With stunning "
					+ "photography and deeply personal writing, Discover connects you with the greatest ideas and minds "
					+ "in science."));
			
			
			
			// Reference
			add( new Book("Desk Reference to the Diagnostic Criteria From DSM-5", "American Psychiatric Association", 
					"05/22/2013", "Reference", 74.00, "/coverImages/diagnosticCriteria", "The Desk Reference to "
							+ "the Diagnostic Criteria From DSM-5 is a concise, affordable companion to the "
							+ "ultimate psychiatric reference, DSM-5. It includes the fully revised diagnostic "
							+ "classification, as well as all of the diagnostic criteria from DSM-5 in an "
							+ "easy-to-use paperback format. This handy reference provides quick access to "
							+ "the information essential to making a diagnosis. Designed to supplement"
							+ " DSM-5, this convenient guide will assist all mental health professionals as "
							+ "they integrate the DSM-5 diagnostic criteria into their diagnoses."));
			
			add( new Book("Merriam-Webster Pocket Thesaurus", "Merriam-Webster", "01/28/2002", "Reference", 5.95, 
					"/coverImages/thesaurus", "A compact compendium of synonyms, related words, and antonyms More "
							+ "than 85,000 words Find the right word whenever you need it."));
			
			add( new Book("Merriam-Webster's Collegiate Dictionary", "Merriam-Webster", "07/01/2003", "Reference", 23.49, 
					"/coverImages/dictionary", "More than 225,000 definitions and over 42,000 usage examples. "
							+ "Includes newly added words and meanings across a variety of fields including technology, "
							+ "entertainment, health, science, and society."));
			
			add( new Book("The Product Manager's Desk Reference", "Steven Haines", "04/29/2021", "Reference", 75.00, 
					"/coverImages/PMDeskRef", "The digital age is here to stay. That means the pace of business change "
							+ "will only increase and competitive forces will challenge you, and your role as a "
							+ "product manager. This is the book that provides the only definitive body of knowledge "
							+ "of product management that you and your product teams can use to optimize your "
							+ "product’s business."));
			
			add( new Book("The Writer's Essential Desk Reference", "Writer’s Digest Books", "01/03/1996", "Reference", 24.99, 
					"/coverImages/writersDeskRef", "This book anticipates questions and gives answers to writers at "
							+ "any stage of their career, covering such topics as establishing a business, researching "
							+ "and polishing manuscripts and selling their work."));
			
			add( new Book("Pharmacy Law Desk Reference", "Albert I. Wertheimer", "11/29/2006", "Reference", 94.95, 
					"/coverImages/pharmacyLawDeskRef", "Your primary source for information on the legal issues of "
							+ "pharmaceutical practice, care, and activity."));
			
			add( new Book("Personal Finance Desk Reference", "Ken Little", "04/03/2007", "Reference", 17.99, 
					"/coverImages/financeDeskRef", "Personal finances are becoming more and more complex.  "
							+ "Beginning with the basics of financial planning (budgeting, interest, banking, "
							+ "insurance, and debt), this helpful guide covers everything people need to know "
							+ "about handling every aspect of their financial world, including investing, "
							+ "taxes, retirement, estate planning, and more."));
			
			add( new Book("Essential World Atlas", "Oxford University Press", "04/01/2021", "Reference", 24.95, 
					"/coverImages/worldAtlas", "With superbly crafted maps covering the entire globe, this new edition "
							+ "of Oxford's Essential World Atlas offers thorough geographical coverage at an affordable "
							+ "price. In addition to a unique city-mapping program, the Essential Atlas features dozens "
							+ "of thematic maps, charts, and graphs that explain many fundamental concepts in human "
							+ "geography."));
			
			add( new Book("Rand McNally Road Atlas 2022", "Rand McNally", "05/06/2021", "Reference", 12.95, 
					"/coverImages/roadAtlas", "North America Road Atlas"));
			
			add( new Book("Oxford Desk Reference: Clinical Genetics and Genomics", "Helen V. Firth", "11/07/2017", 
					"Reference", 115.00, "/coverImages/genetics", "The authors have used their experience to devise "
							+ "a practical clinical approach to many common genetic referrals, both outpatient and "
							+ "ward based. The most common Mendelian disorders, chromosomal disorders, congenital "
							+ "anomalies and syndromes are all covered, and where available diagnostic criteria "
							+ "are included."));
			
			add( new Book("Patent, Copyright & Trademark: An Intellectual Property Desk Reference", "Richard Stim", 
					"04/28/2020", "Reference", 44.99, "/coverImages/intellectualProperty", "Whether you are in the "
							+ "world of business or creative arts, understanding the laws that govern your work "
							+ "is critical to success. But given the convoluted terminology that surrounds patents, "
							+ "copyrights, trademarks, and other intellectual property rights, this isn’t easy. "
							+ "This book explains what legal rights apply to your creations, products, or "
							+ "inventions; different types of patents for inventions from machines to plant "
							+ "clones; the scope of copyright protection; how trademark law works, and what "
							+ "trade-secret law protects."));
			
			add( new Book("The Musical Instrument Desk Reference", "Michael J. Pagliaro", "08/16/2012", 
					"Reference", 89.00, "/coverImages/musicalInstrument", "Descriptions and illustrations of "
							+ "everything from the physics of sound to detailed discussions of each orchestra "
							+ "and band instrument make this work the ideal desktop reference tool for the "
							+ "working musician."));
			
			
		 }};
		 
		 int inserted = mongoUtil.BulkInsertBook(books, ops);
		 
		 System.out.println("Number of books inserted: " + inserted);
		
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
		 * // User User user = new User(); user.setFirstname("Barry");
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
