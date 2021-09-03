package com.nyw.pets.activity.util;

public class GetCouponsUtil {
    String id;
    String couponsImg;

    private String title;
    private String content;
    private String price;
    private String conditionPrice;
    private String has_price;
    private String start_time;
    private String end_time;

    public String getConditionPrice() {
        return conditionPrice;
    }

    public void setConditionPrice(String conditionPrice) {
        this.conditionPrice = conditionPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHas_price() {
        return has_price;
    }

    public void setHas_price(String has_price) {
        this.has_price = has_price;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponsImg() {
        return couponsImg;
    }

    public void setCouponsImg(String couponsImg) {
        this.couponsImg = couponsImg;
    }
}
