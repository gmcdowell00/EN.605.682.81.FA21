package mongobusiness;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "book")
public class Book {
	@Id
	private String id;
	@Field
	private String name;           // name = title
	@Field
	private String shortName;
	@Field
	private String author;
	@Field
	private Date publishedDate;  // mm/dd/yyyy  
	@Field
	private String genre;
	@Field
	private double price;
	@Field
	private String coverImageLink;  // link to the image in webapps/coverImages folder
	@Field
	private String description;
	//@DBRef
	//private Image image;
	//private String img;
	public Book() {}
	
	public Book(String name, String author, Date publishedDate, String genre, double price, 
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCoverImageLink() {
		return coverImageLink;
	}
	public void setCoverImageLink(String coverImageLink) {
		this.coverImageLink = coverImageLink;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}/*
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}*/

	public String getPublishedDateString() {
		if (publishedDate != null) {
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			// match date input format
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			return dateFormat.format(publishedDate);
		} 
		
		return null;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/*
	public String getImg() {
		return this.getImage() == null 
				? ""
				: Base64.getEncoder().encodeToString(this.getImage().getImage().getData());
	}

	public void setImg(String img) {
		this.img = img;
	}*/

}
