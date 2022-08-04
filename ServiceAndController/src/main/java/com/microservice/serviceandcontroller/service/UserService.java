package com.microservice.serviceandcontroller.service;

import com.models.model.User;
import com.models.model.UserList;
import com.models.model.dto.ReserveInfo;
import com.models.model.dto.UserDto;

public interface UserService {
    User save(UserDto userDto);
    User findById(String id);
    UserList findAll();
    void update(UserDto userDto);
    void delete(String id);
    String reserveBook(ReserveInfo reserveInfo);
}
