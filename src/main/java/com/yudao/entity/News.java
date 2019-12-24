package com.yudao.entity;

/**
 * Created by guangzhi.wang on 2019/12/4/004.
 */
public class News {
    private int id;//新闻ID
    private String title;//标题
    private String content;//内容
    private String datetime;//日期时间


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
