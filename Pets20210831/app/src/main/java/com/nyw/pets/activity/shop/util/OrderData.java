package com.nyw.pets.activity.shop.util;

import java.util.List;

public class OrderData {

    String token;
    private List<ShopOrderDataUtil> shop;
    String coupon_id;
    String address_id;
    private List<String> cart_id;

    public List<String> getCart_id() {
        return cart_id;
    }

    public void setCart_id(List<String> cart_id) {
        this.cart_id = cart_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public List<ShopOrderDataUtil> getShop() {
        return shop;
    }

    public void setShop(List<ShopOrderDataUtil> shop) {
        this.shop = shop;
    }
}
