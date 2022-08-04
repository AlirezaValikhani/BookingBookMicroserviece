package com.microservice.serviceandcontroller.service;


import com.models.model.Book;
import com.models.model.BookList;
import com.models.model.dto.BookDto;
import com.models.model.dto.SearchPaginationDto;

public interface BookService {
    Book save(BookDto bookDto);
    Book findById(String id);
    BookList findAll();
    void delete(String id);
    void update(Book book);
    BookList search(SearchPaginationDto searchPaginationDto);
}
