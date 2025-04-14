package com.example.shopgiaythethao.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemsModel implements Serializable {
    private String id;
    private String title;
    private String description;
    private double price;
    private double oldPrice;
    private ArrayList<String> picUrl;
    private ArrayList<String> size;
    private String selectedSize;
    private int numberinCart;

    public ItemsModel() {
    }

    public ItemsModel(String id, String title, String description, double price, double oldPrice, ArrayList<String> picUrl, ArrayList<String> size) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.oldPrice = oldPrice;
        this.picUrl = picUrl;
        this.size = size;
        this.numberinCart = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {