package com.idealstudy.mvp.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.*;

@Data
@Builder
public class PageRequestDto {

    private int page;
    private int size;

    public PageRequestDto(int page, int size){
        this.page = page;
        this.size = size;
    }

    public Pageable getPageable(Sort sort) {

        return PageRequest.of(page - 1, size, sort);
    }
}
