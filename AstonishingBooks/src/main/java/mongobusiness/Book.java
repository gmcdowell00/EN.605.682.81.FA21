package mongobusiness;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "book")
public class Book {
	@Id
	private String id;
	@Field
	private String name;
	@Field
	private String description;
	@Field
	private double price;
	
	public Book() {}
	
	public Book(String name, String description, double price) {	
		this.name = name;
		this.description = description;
		this.price = price;
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
