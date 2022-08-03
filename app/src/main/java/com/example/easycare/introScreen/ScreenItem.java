package com.example.easycare.introScreen;

public class ScreenItem {


    int screenImage,description,title;

    public ScreenItem(int title, int description, int screenImage) {
        this.title = title;
        this.description = description;
        this.screenImage = screenImage;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getScreenImage() {
        return screenImage;
    }

    public void setScreenImage(int screenImage) {
        this.screenImage = screenImage;
    }
}
