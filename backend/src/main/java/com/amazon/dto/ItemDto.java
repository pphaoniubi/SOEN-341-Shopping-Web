package com.amazon.dto;

public class ItemDto {

    private int id;
    private double price;
    private String brand;
    private String description;
    private double rate;
    private String review;
    private boolean inStockStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isInStockStatus() {
        return inStockStatus;
    }

    public void setInStockStatus(boolean inStockStatus) {
        this.inStockStatus = inStockStatus;
    }
}
