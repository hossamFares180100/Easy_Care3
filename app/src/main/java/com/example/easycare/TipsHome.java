package com.example.easycare;

public class TipsHome {
    String title;
    String desc;

    public TipsHome(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public TipsHome() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
