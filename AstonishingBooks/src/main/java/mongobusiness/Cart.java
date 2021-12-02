package mongobusiness;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cart")
public class Cart {
	@Id
	private String id;
	@Field
	private String orderdate;
	@Field 
	private String email;	
	@DBRef
    private List<Book> books;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Cart() {}
	public Cart(List<Book> books) {
		super();
		this.books = books;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}	
	
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String date) {
		this.orderdate = date;
	}
	// Not saving to database
	public double getTotal() {
		
		return books == null || books.size() == 0
				? 0.00
				: books.stream().mapToDouble(book -> book.getPrice()).sum();
	}
}
