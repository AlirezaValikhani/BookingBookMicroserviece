package com.microservice.serviceandcontroller.service.impl;

import com.microservice.serviceandcontroller.service.BookService;
import com.models.model.Book;
import com.models.model.BookList;
import com.models.model.dto.BookDto;
import com.models.model.dto.SearchPaginationDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final RabbitTemplate template;

    public BookServiceImpl(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public Book save(BookDto bookDto) {
        Book book = new Book(null, bookDto.getName(), bookDto.getAuthorName(), false, null);
        return (Book) template.convertSendAndReceive("save_book_queue", book);
    }

    @Override
    public Book findById(String id) {
        return (Book) template.convertSendAndReceive("find_book_by_id_queue", id);
    }


    @Override
    public void update(Book book) {
        template.convertAndSend("update_book_queue", book);
    }

    @Override
    public void delete(String id) {
        template.convertAndSend("delete_book_queue", id);
    }

    @Override
    public BookList findAll() {
        return (BookList) template.convertSendAndReceive("find_all_books_queue", "Message");
    }

    @Override
    public BookList search(SearchPaginationDto searchPaginationDto) {
        return (BookList) template.convertSendAndReceive("search_books_queue", searchPaginationDto);
    }
}
