package com.zjnu.pojo;

public class Order {
    private int id;
    private String buyer;
    private String seller;
    private String  time;
    private String  name;
    private String  brandId;
    private String status;
    private String price;

    public Order(int id, String buyer, String seller, String time, String name, String brandId, String status, String price) {
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.time = time;
        this.name = name;
        this.brandId = brandId;
        this.status = status;
        this.price = price;
    }

    public Order() {
    }

    public Order(int id, String buyer, String seller, String time, String name, String brandId, String status) {
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.time = time;
        this.name = name;
        this.brandId = brandId;
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", brandId='" + brandId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
