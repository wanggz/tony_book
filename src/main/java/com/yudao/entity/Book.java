package com.yudao.entity;

/**
 * Created by guangzhi.wang on 2019/12/17/017.
 */
public class Book {

    private String no;
    private String index;
    private String name;
    private String author;
    private String publisher;
    private String count;
    private String address;

    public Book(String no, String index, String name, String author, String publisher, String count, String address) {
        this.no = no;
        this.index = index;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.count = count;
        this.address = address;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
