package com.microservice.serviceandcontroller.controller;

import com.microservice.serviceandcontroller.service.impl.UserServiceImpl;
import com.models.model.User;
import com.models.model.UserList;
import com.models.model.dto.ReserveInfo;
import com.models.model.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<User> save(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable @RequestParam String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<UserList> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/")
    public void update(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @RequestParam String id) {
        userService.delete(id);
    }
    @PostMapping("/reserveBook")
    public ResponseEntity<String> reserveBook(@RequestBody ReserveInfo reserveInfo) {
        return ResponseEntity.ok(userService.reserveBook(reserveInfo));
    }
}
