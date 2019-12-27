package com.yudao.entity;

import java.util.List;

/**
 * Created by guangzhi.wang on 2019/12/27/027.
 */
public class Result {

    private int numHits;
    private int pageStart;
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
}
