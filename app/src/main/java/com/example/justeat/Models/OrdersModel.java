package com.example.justeat.Models;

public class OrdersModel {

    int orderImage, quantity;
    String orderTo, phone, price, orderNumber, description, foodName;

    public OrdersModel(
            int orderImage,
            String orderTo,
            String phone,
            String price,
            String orderNumber,
            String description,
            String foodName,
            int quantity
    ) {
        this.orderImage = orderImage;
        this.orderTo = orderTo;
        this.phone = phone;
        this.price = price;
        this.orderNumber = orderNumber;
        this.description = description;
        this.foodName = foodName;
        this.quantity = quantity;
    }

    public OrdersModel() {

    }

    public int getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(int orderImage) {
        this.orderImage = orderImage;
    }

    public String getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(String orderTo) {
        this.orderTo = orderTo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String appendString() {
        return "OrdersModel{" +
                "orderImage=" + orderImage +
                ", quantity=" + quantity +
                ", orderTo='" + orderTo + '\'' +
                ", phone='" + phone + '\'' +
                ", price='" + price + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", description='" + description + '\'' +
                ", foodName='" + foodName + '\'' +
                '}';
    }
}
