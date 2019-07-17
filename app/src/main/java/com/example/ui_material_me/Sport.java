package com.example.ui_material_me;

public class Sport {

    private String title;
    private String info;
    private String details;
    private final int imageResource;

    public Sport(String title, String info, int imageResource, String details) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getImageResource(){
        return imageResource;
    }

    public String getDetails(){
        return details;
    }

}
