package mongobusiness;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "payment")
public class Payment {
	
	@Id
	private String id;
	@Field
	private String email;
	@Field
	private String cardname;
	@Field
	private String cardnumber;
	@Field
	private int experiationmonth;
	@Field
	private int experiationyear;
	@Field
	private CardType cardtype;
	
	public Payment() {}
	
	public Payment(String cardNumber, int experiationMonth, int experiationYear, CardType cardType) {
		super();
		this.cardnumber = cardNumber;
		this.experiationmonth = experiationMonth;
		this.experiationyear = experiationYear;
		this.cardtype = cardType;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardNumber() {
		return cardnumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardnumber = cardNumber;
	}
	public int getExperiationMonth() {
		return experiationmonth;
	}
	public void setExperiationMonth(int experiationMonth) {
		this.experiationmonth = experiationMonth;
	}
	public int getExperiationYear() {
		return experiationyear;
	}

	public void setExperiationYear(int experiationYear) {
		this.experiationyear = experiationYear;
	}
	public CardType getCardType() {
		return cardtype;
	}
	public void setCardType(CardType cardType) {
		this.cardtype = cardType;
	}
	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

}
