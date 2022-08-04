package com.reservebook.repository.listener;

import com.models.model.Book;
import com.models.model.BookList;
import com.models.model.User;
import com.models.model.UserList;
import com.models.model.dto.SearchPaginationDto;
import com.reservebook.repository.repository.BookRepository;
import com.reservebook.repository.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageListener {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public MessageListener(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "save_book_queue")
    @SendTo
    public Book saveBookListener(Book book) {
        return bookRepository.save(book);
    }

    @RabbitListener(queues = "find_book_by_id_queue")
    @SendTo
    public Book findBookByIdListener(String id) {
        return bookRepository.findById(id);
    }

    @RabbitListener(queues = "update_book_queue")
    public void updateBookListener(Book book) {
        bookRepository.update(book);
    }

    @RabbitListener(queues = "delete_book_queue")
    public void deleteBookListener(String id) {
        bookRepository.delete(id);
    }

    @RabbitListener(queues = "find_all_books_queue")
    @SendTo
    public BookList findAllBooksListener(String str) {
        List<Book> books = bookRepository.findAll();
        return new BookList(books);
    }

    @RabbitListener(queues = "search_books_queue")
    @SendTo
    public BookList searchBooksListener(SearchPaginationDto searchPaginationDto) {
        List<Book> books = bookRepository.search(searchPaginationDto);
        return new BookList(books);
    }

    @RabbitListener(queues = "save_user_queue")
    @SendTo
    public User saveUserListener(User user) {
        return userRepository.save(user);
    }

    @RabbitListener(queues = "find_user_by_id_queue")
    @SendTo
    public User findUserByIdListener(String id) {
        return userRepository.findById(id);
    }

    @RabbitListener(queues = "update_user_queue")
    public void updateUserListener(User user) {
        userRepository.update(user);
    }

    @RabbitListener(queues = "delete_user_queue")
    public void deleteUserListener(String id) {
        userRepository.delete(id);
    }

    @RabbitListener(queues = "find_all_users_queue")
    @SendTo
    public UserList findAllUsersListener() {
        List<User> users = userRepository.findAll();
        return new UserList(users);
    }
}
