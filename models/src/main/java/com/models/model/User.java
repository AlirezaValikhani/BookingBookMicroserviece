package com.models.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
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

    public User(String id, String fullName, String nationalCode, String email, List<Book> books) {
        this.id = id;
        this.fullName = fullName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.books = books;
    }
}
