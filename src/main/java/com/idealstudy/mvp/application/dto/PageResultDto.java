package com.idealstudy.mvp.application.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<DTO, ENTITY> {

    private List<DTO> dtoList;

    private int totalPage;

    private int page;

    private int size;

    private int startPage, endPage;

    private boolean hasPrev, hasNext;

    private List<Integer> pageList;

    public PageResultDto(Page<ENTITY> result, Function<ENTITY, DTO> fn) {

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        
        this.page = pageable.getPageNumber() + 1;  // 0부터 시작하므로 1 추가해서 1페이지부터 시작하게 표사
        this.size = pageable.getPageSize();

        // 끝 페이지 구하는 임시 식
        int tempEnd = (int) (Math.ceil(page / (double) size)) * size;

        startPage = tempEnd - (size - 1);

        hasPrev = startPage > 1;
        hasNext = totalPage > tempEnd;

        if(totalPage > tempEnd)
            endPage = tempEnd;
        else
            endPage = totalPage;

        pageList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    }
}
