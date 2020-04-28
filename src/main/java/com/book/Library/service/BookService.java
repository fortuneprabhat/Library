package com.book.Library.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.book.Library.model.Book;

public interface BookService extends CrudRepository<Book, Integer>{
	void delete(Book user);
    List<Book> findAll();
    //Book findOne(Integer id);
    Book save(Book user);
}
