package com.microservice.serviceandcontroller.service.impl;

import com.microservice.serviceandcontroller.service.UserService;
import com.models.model.Book;
import com.models.model.User;
import com.models.model.UserList;
import com.models.model.dto.ReserveInfo;
import com.models.model.dto.UserDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final RabbitTemplate template;

    public UserServiceImpl(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getFullName(),userDto.getNationalCode(),userDto.getEmail());
        return (User) template.convertSendAndReceive("save_user_queue",user);
    }

    @Override
    public User findById(String id) {
        return (User) template.convertSendAndReceive("find_user_by_id_queue",id);
    }

    @Override
    public UserList findAll() {
        return (UserList) template.convertSendAndReceive("find_all_users_queue","message");
    }

    @Override
    public void update(UserDto userDto) {
        User user = new User(userDto.getId(),userDto.getFullName(),userDto.getNationalCode(),userDto.getEmail(),null);
        template.convertAndSend("update_user_queue",user);
    }

    @Override
    public void delete(String id) {
        template.convertAndSend("delete_user_queue",id);
    }


    @Override
    public String reserveBook(ReserveInfo reserveInfo) {
        Book book = (Book) template.convertSendAndReceive("find_book_by_id_queue",reserveInfo.getBookId());
        User user = findById(reserveInfo.getUserId());
        if ( book.getIsReserve().equals(true) )
            return "This book is reserved!";
        else {
            book.setId(reserveInfo.getBookId());
            book.setUser(user);
            book.setIsReserve(true);
            template.convertAndSend("update_book_queue",book);
            return "Book reserved successfully!";
        }
    }
}
