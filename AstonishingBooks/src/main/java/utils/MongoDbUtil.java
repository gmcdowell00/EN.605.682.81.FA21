package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.BulkOperations;

import mongobusiness.Book;
import mongobusiness.Cart;
import mongobusiness.Payment;
import mongobusiness.Test;
import mongobusiness.User;
import utils.Constants;

public class MongoDbUtil {

	private List<Book> allBooks;

	public MongoDbUtil() {
		allBooks = new ArrayList<Book>();
	}

	/**
	 * Create or update an existing user.
	 * 
	 * @param entity
	 * @param mongoOperations
	 */
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
			for (Book book : allBooks) {
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

		// get the updated object again
		return mongoOperation.findOne(query, User.class);
	}

	/**
	 * Save or update cart information
	 * 
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
		} else {
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
	 * 
	 * @param payment
	 * @param username
	 * @param mongoOperation
	 * @return
	 */
	public Payment SaveOrUpdatePayment(Payment payment, String email, MongoTemplate mongoOperation) {

		try {
			// Create query
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email)
					.andOperator(Criteria.where("cardname").is(payment.getCardname())));

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
			} else {
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
				user.setPayment(dbPayment);
				;
				mongoOperation.save(user);
			}

			// Return payment information
			return mongoOperation.findOne(query, Payment.class);
		} catch (Exception e) {
			System.out.println("An error has occured");
			System.out.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Updates the first document that matches the query.Updates the first document
	 * that matches the query.
	 * 
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

		// get the updated object again
		return mongoOperation.findOne(query, Test.class);
	}

	/**
	 * Find User in DB by email
	 * 
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
	 * Remove book from bookstore. Only for Admins
	 * 
	 * @param name
	 * @param mongoOperation
	 */
	public void AddOrDeleteBook(String action, Book book, MongoTemplate mongoOperation) {

		Query query = new Query(Criteria.where("name").is(book.getName()));
		switch (action) {
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

	public int BulkInsertBook(List<Book> books, MongoTemplate mongoOperation) {
		return mongoOperation.bulkOps(BulkMode.UNORDERED, Book.class).insert(books).execute().getInsertedCount();
	}

	/**
	 * Returns a list of all books.
	 * 
	 * @param mongoOperation
	 * @return
	 */
	public List<Book> findAllBooks(MongoTemplate mongoOperation) {
		return mongoOperation.findAll(Book.class);
	}

	public List<Book> findBookByCount(int num, MongoTemplate mongoOperation) {
		Query query = new Query();
		query.limit(num);
		return mongoOperation.find(query, Book.class);
	}

	public void PrePopulateBookCollection(MongoTemplate mongoOperation) {
		List<Book> books = new ArrayList<Book>() {
			{

				// declare variables for use in setting date objects
				String date_string = null;
				Date date = null;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

				// Fiction

				date = null;
				date_string = "09/28/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Cloud Cuckoo Land", "Anthony Doerr", date, "Fiction", 23.99,
						"/coverImages/cloudCuckooLand",
						"The heroes of Cloud Cuckoo Land are trying to figure out the world around them: Anna and Omeir, "
								+ "on opposite sides of the formidable city walls during the 1453 siege of Constantinople; "
								+ "teenage idealist Seymour in an attack on a public library in present day Idaho; and Konstance, "
								+ "on an interstellar ship bound for an exoplanet, decades from now."));

				date = null;
				date_string = "06/14/2012";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Complete Sherlock Holmes", "Sir Arthur Conan Doyle", date, "Fiction", 34.95,
						"/coverImages/sherlockHolmes",
						"The game is afoot! Here are the Sherlock Holmes novels and stories, "
								+ "all in one volume and in their proper chronological order, so you can follow the career "
								+ "of the great detective from beginning to end."));

				date = null;
				date_string = "03/20/2018";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Needful Things", "Stephen King", date, "Fiction", 18.99, "/coverImages/needfulThings",
						"The town of Castle Rock, Maine has seen its fair share of oddities over the years, but nothing is as "
								+ "peculiar as the little curio shop that’s just opened for business here. Its mysterious proprietor, "
								+ "Leland Gaunt, seems to have something for everyone out on display at Needful Things... Everyone in "
								+ "town seems willing to make a deal at Needful Things, but the devil is in the details. And no one "
								+ "takes heed of the little sign hanging on the wall: Caveat emptor. In other words, let the buyer beware..."));

				date = null;
				date_string = "10/27/2020";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Picture of Dorian Gray", "Oscar Wilde", date, "Fiction", 10.00,
						"/coverImages/dorianGray",
						"When handsome young Dorian Gray sees a painter’s stunning portrait of him, he is transfixed by its reflection "
								+ "of his own beauty. He says that he would give his soul if only the painting would suffer the ravages of "
								+ "time and he were to remain forever young. From that point on, Dorian lives a life of hedonistic indulgence, "
								+ "knowing that only the painting will show his moral corruption."));

				date = null;
				date_string = "08/26/2003";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Dune", "Frank Herbert", date, "Fiction", 9.99, "/coverImages/dune",
						"Set on the desert planet "
								+ "Arrakis, Dune is the story of the boy Paul Atreides, heir to a noble family tasked with ruling an "
								+ "inhospitable world where the only thing of value is the “spice” melange, a drug capable of extending "
								+ "life and enhancing consciousness. Coveted across the known universe, melange is a prize worth killing for...."));

				date = null;
				date_string = "11/02/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Lord of the Rings", "J. R. R. Tolkien", date, "Fiction", 59.99, "/coverImages/LOTR",
						"In ancient times the Rings of Power were crafted by the Elven-smiths, and Sauron, the Dark Lord, "
								+ "forged the One Ring, filling it with his own power so that he could rule all others. But the One Ring "
								+ "was taken from him, and though he sought it throughout Middle-earth, it remained lost to him. "
								+ "After many ages it fell by chance into the hands of the hobbit Bilbo Baggins.  This new edition is "
								+ "illustrated with J.R.R. Tolkien’s own artwork, created as he wrote the original text."));

				date = null;
				date_string = "06/01/2003";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Candide", "Voltaire", date, "Fiction", 7.95, "/coverImages/candide", "One of the finest "
						+ "satires ever written, Voltaire’s Candide savagely skewers this very “optimistic” approach to life as "
						+ "a shamefully inadequate response to human suffering. The swift and lively tale follows the absurdly "
						+ "melodramatic adventures of the youthful Candide, who is forced into the army, flogged, shipwrecked, "
						+ "betrayed, robbed, separated from his beloved Cunégonde, and tortured by the Inquisition."));

				date = null;
				date_string = "09/01/2007";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Mysterious Rider", "Zane Grey", date, "Fiction", 15.55,
						"/coverImages/mysteriousRider",
						"In The Mysterious Rider, Bill Bellound's foster daughter Columbine agrees to marry his son Jack out of "
								+ "love for her foster father. Jack is a coward, drunkard, gambler, and thief, and Columbine really loves "
								+ "the cowboy Wilson Moore."));

				date = null;
				date_string = "11/16/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Chain of Command", "Tom Clancy", date, "Fiction", 20.96, "/coverImages/chainOfCommand",
						"A shadowy billionaire uses his fortune to further his corrupt ambitions. Along the way, he’s toppled "
								+ "democratically elected governments and exacerbated divisions within stable nations. The competitors "
								+ "he’s destroyed, the people he’s hurt, they’re all just marks on a ledger. Now, he’s ready to implement "
								+ "his most ambitious plan of all. There’s only one force standing in his way—President Jack Ryan."));

				date = null;
				date_string = "04/05/2022";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Sea of Tranquility", "Emily St. John Mandel", date, "Fiction", 11.99,
						"/coverImages/seaOfTranquility",
						"Edwin St. Andrew is eighteen years old when he crosses the Atlantic by "
								+ "steamship, exiled from polite society following an ill-conceived diatribe at a dinner party. "
								+ "He enters the forest, spellbound by the beauty of the Canadian wilderness, and suddenly hears "
								+ "the notes of a violin echoing in an airship terminal—an experience that shocks him to his core."));

				date = null;
				date_string = "11/23/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("ExtraOrdinary", "V. E. Schwab", date, "Fiction", 19.99, "/coverImages/extraOrdinary",
						"Taking place in the years between VICIOUS and VENGEFUL, ExtraOrdinary follows the tale of a teenage "
								+ "girl named Charlotte Tills who following a fatal bus crash, seemingly dies only to wake up to "
								+ "discover she has become an EO — a person with ExtraOrdinary abilities. In Charlotte’s case, it’s "
								+ "the ability to see people's deaths, but when she looks into her own future, sees her own murder at "
								+ "the hands of the self-proclaimed hero and notorious EO killer Eli Ever, who is currently in prison "
								+ "for the murder of Victor Vale. Refusing to accept her fate, Charlotte sets off to find—and change—her "
								+ "future—before it comes for her."));

				date = null;
				date_string = "07/24/2017";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("A Journey to the Center of the Earth", "Jules Verne", date, "Fiction", 15.00,
						"/coverImages/journeyCenterEarth",
						"Accompanied by his nephew Harry and his guide Hans, Professor Von Hardwigg "
								+ "follows directions in a coded message left by sixteenth-century alchemist Arne Saknussemm to the "
								+ "crater of the volcano Sneffels. There they descend into a subterranean world that is home to "
								+ "marvels from the prehistoric past that could never exist in the world above ground."));

				// Non-Fiction
				date = null;
				date_string = "11/02/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Lyrics: 1956 to the Present", "Paul McCartney", date, "Non-Fiction", 100.00,
						"/coverImages/theLyrics",
						"A landmark publishing event featuring two volumes of Paul McCartney's lyrics "
								+ "from his time before The Beatles, with The Beatles, Wings and his solo career — 154 songs in "
								+ "all. Included in the book is McCartney's commentary, never-before-seen photos, handwritten "
								+ "notes, art and ephemera, all of which are part of his personal collection and viewed by the "
								+ "public for the first time."));

				date = null;
				date_string = "11/09/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Will", "Will Smith", date, "Non-Fiction", 23.99, "/coverImages/will", "Will Smith’s "
						+ "transformation from a fearful child in a tense West Philadelphia home to one of the biggest rap stars "
						+ "of his era and then one of the biggest movie stars in Hollywood history, with a string of box office "
						+ "successes that will likely never be broken, is an epic tale of inner transformation and outer triumph, "
						+ "and Will tells it astonishingly well. But it's only half the story."));

				date = null;
				date_string = "05/04/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Madhouse at the End of the Earth", "Julian Sancton", date, "Non-Fiction", 25.49,
						"/coverImages/madhouse",
						"In August 1897, the young Belgian commandant Adrien de Gerlache set sail for "
								+ "a three-year expedition aboard the good ship Belgica with dreams of glory. His destination "
								+ "was the uncharted end of the earth: the icy continent of Antarctica.  After a series of "
								+ "costly setbacks, the commandant faced two bad options: turn back in defeat and spare his "
								+ "men the devastating Antarctic winter, or recklessly chase fame by sailing deeper into the "
								+ "freezing waters. De Gerlache sailed on, and soon the Belgica was stuck fast in the icy"
								+ " hold of the Bellingshausen Sea. When the sun set on the magnificent polar landscape one "
								+ "last time, the ship’s occupants were condemned to months of endless night. In the darkness, "
								+ "plagued by a mysterious illness and besieged by monotony, they descended into madness."));

				date = null;
				date_string = "02/21/2006";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Intelligent Investor", "Benjamin Graham", date, "Non-Fiction", 19.99,
						"/coverImages/intelligentInvestor",
						"The greatest investment advisor of the twentieth century, "
								+ "Benjamin Graham, taught and inspired people worldwide. Graham's philosophy "
								+ "of \"value investing\"—which shields investors from substantial error and teaches them "
								+ "to develop long-term strategies—has made The Intelligent Investor the stock market bible "
								+ "ever since its original publication in 1949."));

				date = null;
				date_string = "12/07/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Fabric of Civilization", "Virginia Postrel", date, "Non-Fiction", 17.99,
						"/coverImages/fabricOfCivilization",
						"In The Fabric of Civilization, Virginia Postrel synthesizes "
								+ "groundbreaking research from archaeology, economics, and science to reveal a surprising "
								+ "history. From Minoans exporting wool colored with precious purple dye to Egypt, to Romans "
								+ "arrayed in costly Chinese silk, the cloth trade paved the crossroads of the ancient world. "
								+ "Textiles funded the Renaissance and the Mughal Empire; they gave us banks and bookkeeping, "
								+ "Michelangelo’s David and the Taj Mahal. The cloth business spread the alphabet and "
								+ "arithmetic, propelled chemical research, and taught people to think in binary code."));

				date = null;
				date_string = "08/04/2015";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Never Cry Wolf", "Farley Mowat", date, "Non-Fiction", 1.99, "/coverImages/neverCryWolf",
						"In 1948, Farley Mowat landed in the far north of Manitoba, Canada, a young biologist sent to "
								+ "investigate the region’s dwindling population of caribou. Many people thought that the caribous’ "
								+ "conspicuous decline had been caused by the tundra’s most notorious predator: the wolf. Alone "
								+ "among the howling canine packs, Mowat expected to find the bloodthirsty beasts of popular conception. "
								+ "Instead, over the course of a summer spent observing the powerful animals, Mowat discovered an "
								+ "animal species with a remarkable capacity for loyalty, virtue, and playfulness."));

				date = null;
				date_string = "04/30/2019";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Rise and Fall of the Dinosaurs", "Steve Brusatte", date, "Non-Fiction", 16.99,
						"/coverImages/riseFallDinosaurs",
						"The dinosaurs. Sixty-six million years ago, the Earth’s most "
								+ "fearsome creatures vanished. Today they remain one of our planet’s great mysteries. "
								+ "Now The Rise and Fall of the Dinosaurs reveals their extraordinary, 200-million-year-long "
								+ "story as never before."));

				date = null;
				date_string = "01/19/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Humble Pi", "Matt Parker", date, "Non-Fiction", 17.00, "/coverImages/humblePi",
						"Our whole world is built on math, from the code running a website to the equations enabling the "
								+ "design of skyscrapers and bridges. Most of the time this math works quietly behind the scenes . . . "
								+ "until it doesn’t. All sorts of seemingly innocuous mathematical mistakes can have significant "
								+ "consequences.  Exploring and explaining a litany of glitches, near misses, and mathematical mishaps "
								+ "involving the internet, big data, elections, street signs, lotteries, the Roman Empire, and an "
								+ "Olympic team, Matt Parker uncovers the bizarre ways math trips us up, and what this reveals about "
								+ "its essential place in our world. Getting it wrong has never been more fun."));

				date = null;
				date_string = "10/26/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("No One Wins Alone", "Mark Messier", date, "Non-Fiction", 22.99,
						"/coverImages/noOneWinsAlone",
						"Mark Messier is one of the most accomplished athletes in the history of professional sports. He was a "
								+ "fierce competitor with a well-earned reputation as a winner. But few people know his real story, not "
								+ "only of the astonishing journey he took to making NHL history, but of the deep understanding of "
								+ "leadership and respect for the power of teamwork he gained."));

				date = null;
				date_string = "09/28/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Baseball 100", "Joe Pasnanski", date, "Non-Fiction", 33.99,
						"/coverImages/baseball100",
						"Longer than Moby-Dick and nearly as ambitious,The Baseball 100 is a one-of-a-kind work by award-winning "
								+ "sportswriter and lifelong student of the game Joe Posnanski that tells the story of the sport through "
								+ "the remarkable lives of its 100 greatest players."));

				date = null;
				date_string = "10/05/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Storyteller", "Dave Grohl", date, "Non-Fiction", 23.99, "/coverImages/storyteller",
						"Dave Grohl, the twice-inducted Rock & Roll Hall of Fame member for his work with Nirvana and the Foo "
								+ "Fighters, decided to tell stories about his life and music on social media. From there, the stories "
								+ "on social media began to coalesce into a whole which made a book possible. He has lived through and "
								+ "seen a lot, so these tales run the gamut from the halcyon Nirvana days, up to the present as a "
								+ "Foo Fighter."));

				date = null;
				date_string = "03/09/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Code Breaker", "Walter Isaacson", date, "Non-Fiction", 27.99,
						"/coverImages/codeBreaker",
						"Jennifer Doudna and her collaborators turned a curiosity of nature into an invention that will transform "
								+ "the human race: an easy-to-use tool that can edit DNA. Known as CRISPR, it opened a brave new world of "
								+ "medical miracles and moral questions.  Should we allow parents, if they can afford it, to enhance the "
								+ "height or muscles or IQ of their kids?"));

				// Magazine
				date = null;
				date_string = "11/22/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("People", "Meredith Corporation", date, "Magazine", 2.04, "/coverImages/people",
						"Celebrity gossip"));

				date = null;
				date_string = "10/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("National Geographic", "National Geographic", date, "Magazine", 1.67,
						"/coverImages/natGeo",
						"Get a glimpse into the exotic and the unexplored with exceptional photography and story-telling."));

				date = null;
				date_string = "11/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Reader’s Digest", "Reader’s Digest Association Inc", date, "Magazine", 1.50,
						"/coverImages/readersDigest",
						"Who can keep up with 8,500 magazines and newspapers, 2,300 TV channels, "
								+ "plus more than 200 million websites? In an era of information overload, Readers Digest offers "
								+ "something unique: the very best advice, information and inspiration from multiple sources, "
								+ "condensed into a consumer-friendly digest."));

				date = null;
				date_string = "11/22/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("TIME Magazine", "Meredith Corporation", date, "Magazine", 0.58,
						"/coverImages/timeMagazine",
						"Offering a rare convergence of incisive reporting, lively writing and world-renowned photography, "
								+ "TIME has been credited with bringing journalism at its best into the fabric of American life."));

				date = null;
				date_string = "11/22/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The New Yorker", "Conde Nast", date, "Magazine", 2.13, "/coverImages/newYorker",
						"The New Yorker is a national weekly magazine that offers a signature mix of reporting and commentary "
								+ "on politics, foreign affairs, business, technology, popular culture, and the arts, along with humor, "
								+ "fiction, poetry, and cartoons."));

				date = null;
				date_string = "11/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Sports Illustrated", "Maven Coalition Inc", date, "Magazine", 0.76,
						"/coverImages/sportsIllustrated",
						"Through emotional storytelling and award-winning photography, "
								+ "Sports Illustrated provides you with complete coverage of all your favorite sports, "
								+ "including the NFL, College Football, Baseball, College Basketball, the NBA and more."));

				date = null;
				date_string = "11/19/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Newsweek", "IBT Media", date, "Magazine", 0.46, "/coverImages/newsweek", "Trusted "
						+ "for more than 75 years, Newsweek provides in depth analysis and insight into the most significant "
						+ "issues of the day. With some of the most influential journalists in the business, Newsweek brings "
						+ "you well-reasoned opinions, expert voices, and incisive reporting that drive and shape global "
						+ "conversations every day, every week, every year."));

				date = null;
				date_string = "12/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Entertainment Weekly", "Meredith Corporation", date, "Magazine", 1.14,
						"/coverImages/entertainmentWeekly",
						"Your essential weekly guide to the latest movies, " + "TV, music, books and more."));

				date = null;
				date_string = "12/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("HGTV Magazine", "Hearst", date, "Magazine", 2.00, "/coverImages/hgtv", "In every "
						+ "issue of HGTV Magazine, you'll learn insider secrets from your favorite HGTV stars. Plus, tour "
						+ "the coolest designer homes from across the country, check out fun decorating tips, DIY "
						+ "solutions, home makeovers, and more!"));

				date = null;
				date_string = "12/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Consumer Reports", "Consumer Reports", date, "Magazine", 2.00,
						"/coverImages/consumerReports",
						"Ratings, recommendations, reliability reports, safety and price "
								+ "comparisons from the world's largest consumer testing center. Helps consumers make "
								+ "better choices for everything from cars to cell phone service. The only magazine of "
								+ "its kind: Expert, independent, nonprofit. 100% unbiased. Consumer Reports accepts no "
								+ "outside advertising."));

				date = null;
				date_string = "11/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Vanity Fair", "Conde Nast", date, "Magazine", 2.50, "/coverImages/vanityFair",
						"From entertainment to world affairs, business to style, design to society, Vanity Fair is a "
								+ "cultural catalyst, inspiring and driving the national conversation."));

				date = null;
				date_string = "11/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Discover", "Kalmbach Publishing Co", date, "Magazine", 3.12, "/coverImages/discover",
						"Discover magazine takes you on the most exciting adventure of our times: The scientific quest to "
								+ "uncover our origins, understand the world around us, and build a better future. With stunning "
								+ "photography and deeply personal writing, Discover connects you with the greatest ideas and minds "
								+ "in science."));

				// Reference
				date = null;
				date_string = "05/22/2013";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Desk Reference to the Diagnostic Criteria From DSM-5", "American Psychiatric Association",
						date, "Reference", 74.00, "/coverImages/diagnosticCriteria",
						"The Desk Reference to "
								+ "the Diagnostic Criteria From DSM-5 is a concise, affordable companion to the "
								+ "ultimate psychiatric reference, DSM-5. It includes the fully revised diagnostic "
								+ "classification, as well as all of the diagnostic criteria from DSM-5 in an "
								+ "easy-to-use paperback format. This handy reference provides quick access to "
								+ "the information essential to making a diagnosis. Designed to supplement"
								+ " DSM-5, this convenient guide will assist all mental health professionals as "
								+ "they integrate the DSM-5 diagnostic criteria into their diagnoses."));

				date = null;
				date_string = "01/28/2002";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Merriam-Webster Pocket Thesaurus", "Merriam-Webster", date, "Reference", 5.95,
						"/coverImages/thesaurus", "A compact compendium of synonyms, related words, and antonyms More "
								+ "than 85,000 words Find the right word whenever you need it."));

				date = null;
				date_string = "07/01/2003";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Merriam-Webster's Collegiate Dictionary", "Merriam-Webster", date, "Reference", 23.49,
						"/coverImages/dictionary",
						"More than 225,000 definitions and over 42,000 usage examples. "
								+ "Includes newly added words and meanings across a variety of fields including technology, "
								+ "entertainment, health, science, and society."));

				date = null;
				date_string = "04/29/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Product Manager's Desk Reference", "Steven Haines", date, "Reference", 75.00,
						"/coverImages/PMDeskRef",
						"The digital age is here to stay. That means the pace of business change "
								+ "will only increase and competitive forces will challenge you, and your role as a "
								+ "product manager. This is the book that provides the only definitive body of knowledge "
								+ "of product management that you and your product teams can use to optimize your "
								+ "product’s business."));

				date = null;
				date_string = "01/03/1996";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Writer's Essential Desk Reference", "Writer’s Digest Books", date, "Reference", 24.99,
						"/coverImages/writersDeskRef",
						"This book anticipates questions and gives answers to writers at "
								+ "any stage of their career, covering such topics as establishing a business, researching "
								+ "and polishing manuscripts and selling their work."));

				date = null;
				date_string = "11/29/2006";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Pharmacy Law Desk Reference", "Albert I. Wertheimer", date, "Reference", 94.95,
						"/coverImages/pharmacyLawDeskRef", "Your primary source for information on the legal issues of "
								+ "pharmaceutical practice, care, and activity."));

				date = null;
				date_string = "04/03/2007";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Personal Finance Desk Reference", "Ken Little", date, "Reference", 17.99,
						"/coverImages/financeDeskRef",
						"Personal finances are becoming more and more complex.  "
								+ "Beginning with the basics of financial planning (budgeting, interest, banking, "
								+ "insurance, and debt), this helpful guide covers everything people need to know "
								+ "about handling every aspect of their financial world, including investing, "
								+ "taxes, retirement, estate planning, and more."));

				date = null;
				date_string = "04/01/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Essential World Atlas", "Oxford University Press", date, "Reference", 24.95,
						"/coverImages/worldAtlas",
						"With superbly crafted maps covering the entire globe, this new edition "
								+ "of Oxford's Essential World Atlas offers thorough geographical coverage at an affordable "
								+ "price. In addition to a unique city-mapping program, the Essential Atlas features dozens "
								+ "of thematic maps, charts, and graphs that explain many fundamental concepts in human "
								+ "geography."));

				date = null;
				date_string = "05/06/2021";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Rand McNally Road Atlas 2022", "Rand McNally", date, "Reference", 12.95,
						"/coverImages/roadAtlas", "North America Road Atlas"));

				date = null;
				date_string = "11/07/2017";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Oxford Desk Reference: Clinical Genetics and Genomics", "Helen V. Firth", date,
						"Reference", 115.00, "/coverImages/genetics",
						"The authors have used their experience to devise "
								+ "a practical clinical approach to many common genetic referrals, both outpatient and "
								+ "ward based. The most common Mendelian disorders, chromosomal disorders, congenital "
								+ "anomalies and syndromes are all covered, and where available diagnostic criteria "
								+ "are included."));

				date = null;
				date_string = "04/28/2020";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("Patent, Copyright & Trademark: An Intellectual Property Desk Reference", "Richard Stim",
						date, "Reference", 44.99, "/coverImages/intellectualProperty",
						"Whether you are in the "
								+ "world of business or creative arts, understanding the laws that govern your work "
								+ "is critical to success. But given the convoluted terminology that surrounds patents, "
								+ "copyrights, trademarks, and other intellectual property rights, this isn’t easy. "
								+ "This book explains what legal rights apply to your creations, products, or "
								+ "inventions; different types of patents for inventions from machines to plant "
								+ "clones; the scope of copyright protection; how trademark law works, and what "
								+ "trade-secret law protects."));

				date = null;
				date_string = "08/16/2012";
				try {
					date = formatter.parse(date_string);
				} catch (Exception e) {
					System.out.println("An error has occured");
					System.out.println(e.getMessage());
				}
				add(new Book("The Musical Instrument Desk Reference", "Michael J. Pagliaro", date, "Reference", 89.00,
						"/coverImages/musicalInstrument",
						"Descriptions and illustrations of "
								+ "everything from the physics of sound to detailed discussions of each orchestra "
								+ "and band instrument make this work the ideal desktop reference tool for the "
								+ "working musician."));

			}
		};

		int inserted = this.BulkInsertBook(books, mongoOperation);

		System.out.println("Number of books inserted: " + inserted);
	}
}
