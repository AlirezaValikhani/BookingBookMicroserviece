package com.models.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book implements Serializable {
    private String id;
    private String name;
    private String authorName;
    private Boolean isReserve;
    private User user;

    public Book(String name, String authorName, Boolean isReserve) {
        this.name = name;
        this.authorName = authorName;
        this.isReserve = isReserve;
    }
}
