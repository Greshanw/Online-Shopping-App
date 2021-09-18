package com.example.project_mad.models;

public class    RecommendedModel {
    String name;
    String img_url;
    int price;
    String rating;

    public RecommendedModel() {
    }

    public RecommendedModel(String name, String img_url, int price, String rating) {
        this.name = name;
        this.img_url = img_url;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
