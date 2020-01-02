package com.yudao.entity;

import java.util.List;

/**
 * Created by guangzhi.wang on 2019/12/27/027.
 */
public class Result {

    private int numHits;
    private int pageStart;
    private int pageSize;
    private int pageNoNow;
    private List<Integer> pageNoList;
    private List<Book> books;

    public int getNumHits() {
        return numHits;
    }

    public void setNumHits(int numHits) {
        this.numHits = numHits;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNoNow() {
        return pageNoNow;
    }

    public void setPageNoNow(int pageNoNow) {
        this.pageNoNow = pageNoNow;
    }

    public List<Integer> getPageNoList() {
        return pageNoList;
    }

    public void setPageNoList(List<Integer> pageNoList) {
        this.pageNoList = pageNoList;
    }
}
