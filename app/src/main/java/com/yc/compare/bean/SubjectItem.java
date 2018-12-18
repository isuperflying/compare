package com.yc.compare.bean;

import java.util.List;

/**
 * Created by iflying on 2018/2/6.
 */

public class SubjectItem {
    private String current;
    private String pages;
    private List<RecordItem> records;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<RecordItem> getRecords() {
        return records;
    }

    public void setRecords(List<RecordItem> records) {
        this.records = records;
    }
}
