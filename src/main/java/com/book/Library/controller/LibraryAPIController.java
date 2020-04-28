package com.book.Library.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.Library.model.Book;
import com.book.Library.service.BookService;

//import com.nagarro.B_friends.model.Friend;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/"})
public class LibraryAPIController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/home")
	public String home() {
		return "<h1>Home Page </h1>";
	}
	
	@GetMapping("/book")
	public Iterable<Book> getAllBooks(){
		return bookService.findAll();
	}
	
	@GetMapping("/book/{id}")
	public Optional<Book> getbook(@PathVariable(value = "id") int id){
		return bookService.findById(id);
	}
	
	@PostMapping("/book")
	public void addbook(@Valid @RequestBody Book book)throws ValidationException {
		if((book.getTitle()!=null)||(book.getAuthor()!=null)) {
			bookService.save(book);
			return;
		}
		throw new ValidationException("Bad Request");
	}
	
	
	@PutMapping("/book")
	public ResponseEntity<Book> updateBook(@RequestBody Book book){
		if(bookService.findById(book.getId()).isPresent()) {
			return new ResponseEntity<Book>(bookService.save(book), HttpStatus.OK);
		}
		return new ResponseEntity<Book>(book, HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<Boolean> deletebook(@PathVariable(value = "id")  int id){
		if(bookService.findById(id).isPresent()) {
			bookService.deleteById(id);
			return new ResponseEntity<>(true, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		
	}
}