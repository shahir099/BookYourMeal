package com.example.shahir.orderyourfood.Model;

public class Rating {
    private  String userPhone; /// key + value

    private  String foodId;
    private  String rateValue;
    private  String commnet;

    public Rating() {
    }

    public Rating(String userPhone, String foodId, String rateValue, String commnet) {

        this.userPhone = userPhone;
        this.foodId = foodId;
        this.rateValue = rateValue;
        this.commnet = commnet;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet;
    }
}
