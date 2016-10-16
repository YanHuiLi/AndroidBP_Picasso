package com.example.archer.picasso_listview.entity;

/**
 * Created by Archer on 2016/10/15.
 * <p>
 * 描述:将要解析的数据的名称应该是一致的
 * <p>
 * 作者
 *
 * http://litchiapi.jstv.com/api/GetFeeds?column=17&PageSize=20&pageIndex=1&val=AD908EDAB9C3ED111A58AF86542CCF50
 */

public class Item {
 private  String subject;
    private String summary;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    private String cover;

}
