package com.reservebook.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchPaginationDto {
    private String search;
    private Integer size;
    private Integer page;
}
