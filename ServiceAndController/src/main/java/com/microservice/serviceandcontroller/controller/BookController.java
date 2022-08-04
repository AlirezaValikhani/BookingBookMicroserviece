package com.microservice.serviceandcontroller.controller;

import com.microservice.serviceandcontroller.service.impl.BookServiceImpl;
import com.models.model.Book;
import com.models.model.BookList;
import com.models.model.dto.BookDto;
import com.models.model.dto.SearchPaginationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/")
    public ResponseEntity<Book> save(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.save(bookDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable @RequestParam String id) {
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<BookList> findAll() {
        return ResponseEntity.ok().body(bookService.findAll());
    }

    @PutMapping("/")
    public void update(@RequestBody Book book) {
        bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<BookList> search(@RequestBody SearchPaginationDto search) {
        return ResponseEntity.ok().body(bookService.search(search));
    }
}
