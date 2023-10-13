package com.nan.board.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;


@Getter
@NoArgsConstructor
public class PageOption {
    private int pageNum;
    private int size;
    private Sort sort;
    public PageOption(int pageNum)
    {
        this.pageNum = pageNum;
        Sort.Direction direction = Sort.Direction.ASC;
        this.size = 10;
        this.sort = Sort.by(direction, "createdDate");
    }
    public PageOption(int pageNum, String property)
    {
        this.pageNum = pageNum;
        Sort.Direction direction = Sort.Direction.ASC;
        this.size = 10;
        this.sort = Sort.by(direction, property);
    }

}
