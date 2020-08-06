package com.example.bringappfinal;

public class IntroScreenItem {
    String Title,Description;//of intro slider
    int Slider_image;
    public IntroScreenItem(String title,String description,int slider_image)
    {
        Title=title;
        Description=description;
        Slider_image=slider_image;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setSlider_image(int slider_image) {
        Slider_image = slider_image;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getSlider_image() {
        return Slider_image;
    }
}
