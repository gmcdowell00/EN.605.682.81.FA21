package mongobusiness;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "test")
public class Test {

	@Id
	private String id;
	@Field
	private String name;
	@Field
	private String description;


	public Test() {}
	public Test(String name, String description) {
		this.name = name;
		this.description = description;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public String setDescription(String description) {
		return description;
	}

}
