package com.reservebook.repository.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfiguration {
    public static final String  SAVE_BOOK_QUEUE = "save_book_queue";
    public static final String FIND_BOOK_BY_ID_QUEUE = "find_book_by_id_queue";
    public static final String UPDATE_BOOK_QUEUE = "update_book_queue";
    public static final String DELETE_BOOK_QUEUE = "delete_book_queue";
    public static final String FIND_ALL_BOOKS_QUEUE = "find_all_books_queue";
    public static final String SEARCH_BOOKS_QUEUE = "search_books_queue";
    public static final String  SAVE_USER_QUEUE = "save_user_queue";
    public static final String FIND_USER_BY_ID_QUEUE = "find_user_by_id_queue";
    public static final String UPDATE_USER_QUEUE = "update_user_queue";
    public static final String DELETE_USER_QUEUE = "delete_user_queue";
    public static final String FIND_ALL_USERS_QUEUE = "find_all_users_queue";
    public static final String SEARCH_USERS_QUEUE = "search_users_queue";

    @Bean
    public Queue saveBookQueue() {
        return new Queue(SAVE_BOOK_QUEUE);
    }

    @Bean
    public Queue findBookByIdQueue() {
        return new Queue(FIND_BOOK_BY_ID_QUEUE);
    }

    @Bean
    public Queue updateBookQueue() {
        return new Queue(UPDATE_BOOK_QUEUE);
    }

    @Bean
    public Queue deleteBookQueue() {
        return new Queue(DELETE_BOOK_QUEUE);
    }

    @Bean
    public Queue findAllBooksQueue() {
        return new Queue(FIND_ALL_BOOKS_QUEUE);
    }

    @Bean
    public Queue searchBooksQueue() {
        return new Queue(SEARCH_BOOKS_QUEUE);
    }

    @Bean
    public Queue saveUserQueue() {
        return new Queue(SAVE_USER_QUEUE);
    }

    @Bean
    public Queue findUserByIdQueue() {
        return new Queue(FIND_USER_BY_ID_QUEUE);
    }

    @Bean
    public Queue updateUserQueue() {
        return new Queue(UPDATE_USER_QUEUE);
    }

    @Bean
    public Queue deleteUserQueue() {
        return new Queue(DELETE_USER_QUEUE);
    }

    @Bean
    public Queue findAllUsersQueue() {
        return new Queue(FIND_ALL_USERS_QUEUE);
    }

    @Bean
    public Queue searchUsersQueue() {
        return new Queue(SEARCH_USERS_QUEUE);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
