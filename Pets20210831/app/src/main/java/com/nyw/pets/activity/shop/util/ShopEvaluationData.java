package com.nyw.pets.activity.shop.util;

import java.util.List;

public class ShopEvaluationData {
    String token;
    String order_id;
    List<ShopEvaluationInfoData> shop;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<ShopEvaluationInfoData> getShop() {
        return shop;
    }

    public void setShop(List<ShopEvaluationInfoData> shop) {
        this.shop = shop;
    }
}
