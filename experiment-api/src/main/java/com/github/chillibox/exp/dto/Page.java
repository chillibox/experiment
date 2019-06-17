package com.github.chillibox.exp.dto;

import java.util.List;

public class Page<T> {

    private int total;
    private int pageNum;
    private int pageSize;
    private List<T> page;

    public Page(int total, int pageNum, int pageSize, List<T> page) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }


}
