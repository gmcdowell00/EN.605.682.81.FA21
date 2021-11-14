package contextlisteners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

public class DbContextListener implements ServletContextListener {

	private static final String DATABASE_URL = "mongodb+srv://admin:admin@sandbox.fh9hs.mongodb.net/astonishingbooks?retryWrites=true&w=majority";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		ServletContext context = event.getServletContext();
		ConnectionString connectionString = new ConnectionString(DATABASE_URL);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.build();

		MongoClient client = MongoClients.create(mongoClientSettings);
		MongoTemplate ops = new MongoTemplate(client, "astonishingbooks");
		context.setAttribute("Database", ops);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

}
