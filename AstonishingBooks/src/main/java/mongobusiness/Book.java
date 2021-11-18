package mongobusiness;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "book")
public class Book {
	@Id
	private String id;
	@Field
	private String name;           // name = title
	@Field
	private String author;
	@Field
	private String publishedDate;  // mm/dd/yyyy  
	@Field
	private String genre;
	@Field
	private double price;
	@Field
	private String coverImageLink;  // link to the image in webapps/coverImages folder
	@Field
	private String description;

	
	public Book() {}
	
	public Book(String name, String author, String publishedDate, String genre, double price, 
				String coverImageLink, String description) {	
		this.name = name;
		this.author = author;
		this.publishedDate = publishedDate;
		this.genre = genre;
		this.price = price;
		this.coverImageLink = coverImageLink;
		this.description = description;

	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	
}
