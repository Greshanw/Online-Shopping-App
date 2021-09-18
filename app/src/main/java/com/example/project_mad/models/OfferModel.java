package com.example.project_mad.models;

public class OfferModel {
    String discount;
    String img_url;

    public OfferModel() {
    }

    public OfferModel(String discount, String img_url) {
        this.discount = discount;
        this.img_url = img_url;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
