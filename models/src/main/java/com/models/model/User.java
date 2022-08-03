package com.models.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String id;
    private String fullName;
    private String nationalCode;
    private String email;
    private List<Book> books;

    public User(String fullName, String nationalCode, String email) {
        this.fullName = fullName;
        this.nationalCode = nationalCode;
        this.email = email;
    }
}
