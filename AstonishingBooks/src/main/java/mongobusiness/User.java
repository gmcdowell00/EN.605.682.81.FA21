package mongobusiness;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
public class User {
	@Id
	private String id;			
	@Field
	private String firstname;
	@Field
	private String lastname;
	@Field
	private String email;
	@Field 
	private String password;
	@Field
	private String address;
	@Field
	private String city;
	@Field
	private String state;
	@Field
	private String country;
	@Field
	private String zip;
	@Field
	private boolean isAdmin;
	@DBRef
	private Payment payment;
	@DBRef
	private  Cart cart;
	@DBRef
    private List<Book> wishlist;
	
	public User() {}
	
	public User(String firstname, String lastname, String email, boolean isAdmin, String password, 
			String address, String city, String state, String country, String zip, Payment payment, 
			Cart cart, List<Book> wishlist) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.isAdmin = isAdmin;
		this.password = password;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;		
		this.payment = payment;
		this.cart = cart;
		this.wishlist = wishlist;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<Book> getWishlist() {
		return wishlist;
	}

	public void setWishlist(List<Book> wishlist) {
		this.wishlist = wishlist;
	}
	
}
