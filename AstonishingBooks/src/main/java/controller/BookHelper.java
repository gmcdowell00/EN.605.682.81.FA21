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
		books.sort(Comparator.comparing(bookObject -> bookObject.getPublishedDate())); 
		
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
	
	public List<Book> genreBooks(List<Book> books, String genre){
		
		// number of books passed in
		int totalBookCount = books.size();
		
		// create a list to return the sorted books
		List<Book> sortedBooks = new ArrayList<>();
		
		// initialize the index for the sortedBook list
		int index = 0;
		
		// add the books to the list if the genre matches
		for (int counter = 0; counter < totalBookCount; counter++) {
			if (books.get(counter).getGenre().equals(genre)) {
				sortedBooks.add(index, books.get(counter));
			}
		}
		
		// return the book sorted by genre
		return sortedBooks;
		
	}
	
	
}
