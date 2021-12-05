package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import mongobusiness.Book;
import utils.Constants;

public class BookHelper {
	
	public List<Book> newestBooks(List<Book> books){
		
		// number of books passed in
		int totalBookCount = books.size();
		
		// sort the books on date - results in oldest to newest list
		// Added by Glen - If  publish date is last put at end
		books.sort(Comparator.nullsLast(Comparator.comparing(bookObject -> bookObject.getPublishedDate()))); 
		
		// create lists for reversing the order and for excluding magazines
		List<Book> tempBooks = new ArrayList<>();
		List<Book> sortedBooks = new ArrayList<>();
		
		// loop through the books and save them in reverse order (newest to oldest)
		for (int counter = totalBookCount - 1; counter >= 0 ; counter--) {
				tempBooks.add((totalBookCount - 1 - counter), books.get(counter));
		}
		
		// keep only the first (newest) 12 that are not magazines
		int index = 0;
		int counter = 0;
		while (index < 12) {
			if (!tempBooks.get(counter).getGenre().equals("Magazine")) {
				sortedBooks.add(index, tempBooks.get(counter));
				index++;
			}
			counter++;
		}

		// return the list of the 12 newest non-magazine results
		return sortedBooks;		
	}
	
	
	public List<Book> searchBooks(List<Book> books, String searchQuery){
		
		// set the search term to lower case so the search will ignore case
		String searchTerm = searchQuery.toLowerCase();
		
		// create a list to return the results
		List<Book> results = new ArrayList<>();
		
		// add the book to the list if the searchTerm is contained in a field
		for (int counter = 0; counter < books.size(); counter++) {
			if (books.get(counter).getName().toLowerCase().contains(searchTerm) 
					|| books.get(counter).getAuthor().toLowerCase().contains(searchTerm) 
					|| books.get(counter).getGenre().toLowerCase().equals(searchTerm)) {
				results.add(books.get(counter));
			}
		}
		
		// return the book sorted by genre
		return results;		
	}
	
	
	public Book bookById(List<Book> books, String bookId){
				
		// create a list to return the results
		Book result = new Book();
		
		// add the book to the list if the searchTerm is contained in a field
		for (int counter = 0; counter < books.size(); counter++) {
			if (books.get(counter).getId().equals(bookId)) {
				result = books.get(counter);
			}

		}
		
		// return the book sorted by genre
		return result;		
	}
	
}
